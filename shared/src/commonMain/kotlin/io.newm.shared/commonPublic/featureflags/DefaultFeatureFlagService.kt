@file:OptIn(ExperimentalTime::class)

package io.newm.shared.commonPublic.featureflags

import io.newm.shared.NewmAppLogger
import io.newm.shared.commonInternal.db.PreferencesDataStore
import io.newm.shared.commonPublic.models.User
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

// Production-ready implementation
class DefaultFeatureFlagService(
    private val dataSource: FeatureFlagDataSource,
    private val preferencesStore: PreferencesDataStore,
    private val logger: NewmAppLogger,
    private val cacheConfig: CacheConfig = CacheConfig()
) : FeatureFlagService {

    companion object {
        private const val OVERRIDE_PREFIX = "dev_override_"
        private const val MAX_EVALUATION_HISTORY = 100
        private val TAG = "DefaultFeatureFlagService"
    }

    data class CacheConfig(
        val ttl: Duration = 5.minutes,
        val maxSize: Int = 1000
    )

    // Thread-safe state management
    private val mutex = Mutex()
    private val cache = mutableMapOf<String, CacheEntry>()
    private val evaluationHistory = mutableListOf<FlagEvaluation>()
    private val currentUser = atomic<User?>(null)

    // Reactive state flows
    private val _flagStates = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    private val _userState = MutableStateFlow<User?>(null)

    override suspend fun setUser(user: User): FlagResult<Unit> {
        return try {
            logger.breadcrumb("FeatureFlagService", "Setting user: ${user.id}")

            val result = dataSource.identifyUser(user)
            when (result) {
                is FlagResult.Success -> {
                    currentUser.value = user
                    _userState.value = user
                    invalidateCache() // Clear cache when user changes
                    FlagResult.Success(Unit)
                }

                is FlagResult.Error -> {
                    logger.error(TAG, "Failed to set user", result.exception)
                    FlagResult.Error(result.exception)
                }
            }
        } catch (e: Exception) {
            logger.error(TAG, "Unexpected error setting user", e)
            FlagResult.Error(e)
        }
    }

    override suspend fun isEnabled(flag: FeatureFlag): FlagResult<Boolean> {
        return try {
            // Check local override first
            val override = getLocalOverride(flag.key)
            if (override != null) {
                recordEvaluation(flag.key, override, EvaluationSource.LOCAL_OVERRIDE)
                logger.breadcrumb(
                    "FeatureFlagService",
                    "Override for ${flag.displayName}: $override"
                )
                return FlagResult.Success(override)
            }

            // Check cache
            val cachedValue = getCachedValue(flag.key)
            if (cachedValue != null) {
                recordEvaluation(flag.key, cachedValue, EvaluationSource.CACHE)
                return FlagResult.Success(cachedValue)
            }

            // Fetch from remote source
            val result = dataSource.getBooleanVariation(flag)
            result.fold(
                onSuccess = { value ->
                    setCachedValue(flag.key, value)
                    recordEvaluation(flag.key, value, EvaluationSource.REMOTE_SOURCE)
                    updateFlagState(flag.key, value)
                },
                onError = { exception, fallback ->
                    val fallbackValue = fallback ?: flag.defaultValue
                    logger.error(TAG, "Failed to fetch flag ${flag.key}, using fallback", exception)
                    recordEvaluation(flag.key, fallbackValue, EvaluationSource.FALLBACK)
                }
            )

            result
        } catch (e: Exception) {
            logger.error(TAG, "Unexpected error evaluating flag ${flag.key}", e)
            val fallback = flag.defaultValue
            recordEvaluation(flag.key, fallback, EvaluationSource.FALLBACK)
            FlagResult.Error(e, fallback)
        }
    }

    override fun observeFlag(flag: FeatureFlag): Flow<Boolean> {
        return _flagStates
            .map { states -> states[flag.key] ?: flag.defaultValue }
            .let { flow ->
                // Simple distinctUntilChanged implementation
                var lastValue: Boolean? = null
                flow.filter { value ->
                    if (lastValue != value) {
                        lastValue = value
                        true
                    } else {
                        false
                    }
                }
            }
    }

    override fun observeAllFlags(): Flow<Map<String, Boolean>> {
        return _flagStates.asStateFlow()
    }

    override fun getAllFlags(): List<FeatureFlag> = FeatureFlags.ALL_FLAGS

    override suspend fun getLocalOverride(flagKey: String): Boolean? {
        return try {
            preferencesStore.getBoolean(OVERRIDE_PREFIX + flagKey)
        } catch (e: Exception) {
            logger.error(TAG, "Failed to get local override for $flagKey", e)
            null
        }
    }

    override suspend fun setLocalOverride(flagKey: String, value: Boolean?): FlagResult<Unit> {
        return try {
            val prefKey = OVERRIDE_PREFIX + flagKey

            if (value == null) {
                preferencesStore.deleteValue(prefKey)
                logger.breadcrumb("FeatureFlagService", "Override cleared for $flagKey")
            } else {
                preferencesStore.saveBoolean(prefKey, value)
                logger.breadcrumb("FeatureFlagService", "Override set for $flagKey to $value")
                updateFlagState(flagKey, value)
            }

            FlagResult.Success(Unit)
        } catch (e: Exception) {
            logger.error(TAG, "Failed to set local override for $flagKey", e)
            FlagResult.Error(e)
        }
    }


    override suspend fun resetAllOverrides(): FlagResult<Unit> {
        return try {
            logger.breadcrumb("FeatureFlagService", "Resetting all local overrides")

            FeatureFlags.ALL_FLAGS.forEach { flag ->
                val prefKey = OVERRIDE_PREFIX + flag.key
                preferencesStore.deleteValue(prefKey)
            }

            invalidateCache()
            FlagResult.Success(Unit)
        } catch (e: Exception) {
            logger.error("", message = "Failed to reset all overrides", exception = e)
            FlagResult.Error(e)
        }
    }

    override suspend fun getEffectiveValue(flag: FeatureFlag): FlagResult<Boolean> {
        return isEnabled(flag) // Uses the same logic with all rules applied
    }

    override suspend fun exportDebugState(): Map<String, Any> {
        return mutex.withLock {
            mapOf(
                "currentUser" to (currentUser.value?.id ?: "none"),
                "cacheSize" to cache.size,
                "evaluationHistorySize" to evaluationHistory.size,
                "allFlags" to FeatureFlags.ALL_FLAGS.associate { flag ->
                    flag.key to mapOf(
                        "displayName" to flag.displayName,
                        "category" to flag.category.name,
                        "cachedValue" to getCachedValue(flag.key),
                        "localOverride" to getLocalOverride(flag.key)
                    )
                },
                "timestamp" to Clock.System.now().toString()
            )
        }
    }

    override fun getEvaluationHistory(): List<FlagEvaluation> {
        return evaluationHistory.toList() // Return defensive copy
    }

    // Private helper methods
    private suspend fun getCachedValue(flagKey: String): Boolean? {
        return mutex.withLock {
            val entry = cache[flagKey]
            if (entry != null && !entry.isExpired(Clock.System.now())) {
                entry.value
            } else {
                cache.remove(flagKey)
                null
            }
        }
    }

    private suspend fun setCachedValue(flagKey: String, value: Boolean) {
        mutex.withLock {
            // Implement LRU eviction if cache is full
            if (cache.size >= cacheConfig.maxSize) {
                val oldestKey = cache.entries
                    .minByOrNull { it.value.timestamp }
                    ?.key
                oldestKey?.let { cache.remove(it) }
            }

            cache[flagKey] = CacheEntry(
                value = value,
                timestamp = Clock.System.now(),
                ttl = cacheConfig.ttl
            )
        }
    }

    private suspend fun invalidateCache() {
        mutex.withLock {
            cache.clear()
        }
    }

    private fun updateFlagState(flagKey: String, value: Boolean) {
        val currentStates = _flagStates.value.toMutableMap()
        currentStates[flagKey] = value
        _flagStates.value = currentStates
    }

    private fun recordEvaluation(flagKey: String, result: Boolean, source: EvaluationSource) {
        val evaluation = FlagEvaluation(
            flagKey = flagKey,
            result = result,
            source = source,
            timestamp = Clock.System.now(),
            userId = currentUser.value?.id
        )

        // Maintain bounded history
        if (evaluationHistory.size >= MAX_EVALUATION_HISTORY) {
            evaluationHistory.removeAt(0)
        }
        evaluationHistory.add(evaluation)
    }
}