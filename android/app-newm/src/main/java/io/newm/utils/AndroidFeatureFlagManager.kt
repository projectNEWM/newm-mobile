@file:OptIn(kotlin.time.ExperimentalTime::class)

package io.newm.utils

import android.app.Application
import com.launchdarkly.sdk.ContextKind
import com.launchdarkly.sdk.LDContext
import com.launchdarkly.sdk.android.LDClient
import com.launchdarkly.sdk.android.LDConfig
import com.launchdarkly.sdk.android.LDConfig.Builder.AutoEnvAttributes
import io.newm.shared.NewmAppLogger
import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.shared.commonInternal.db.PreferencesDataStore
import io.newm.shared.commonPublic.featureflags.FeatureFlag
import io.newm.shared.commonPublic.featureflags.FeatureFlagDataSource
import io.newm.shared.commonPublic.featureflags.FeatureFlags
import io.newm.shared.commonPublic.featureflags.FlagResult
import io.newm.shared.commonPublic.models.User
import io.newm.shared.util.asDeferred
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.pow
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant

/**
 * Android implementation of FeatureFlagDataSource using LaunchDarkly SDK.
 *
 * Key features:
 * - Non-blocking async initialization
 * - Real-time flag updates via listeners
 * - Disk persistence for offline support
 * - Consistent 5-minute cache timeout
 * - Retry logic with exponential backoff
 * - Circuit breaker to prevent hammering LaunchDarkly on failures
 * - Thread-safe operations
 */
class AndroidFeatureFlagManager(
    private val application: Application,
    private val sharedBuildConfig: NewmSharedBuildConfig,
    private val preferencesStore: PreferencesDataStore,
    private val coroutineScope: CoroutineScope,
    private val log: NewmAppLogger
) : FeatureFlagDataSource {

    companion object {
        private const val TAG = "AndroidFeatureFlagManager"
        private const val DISK_CACHE_PREFIX = "flag_cache_"
        private const val DISK_CACHE_TIMESTAMP_PREFIX = "flag_cache_ts_"

        // Circuit breaker configuration
        private const val CIRCUIT_BREAKER_FAILURE_THRESHOLD = 5
        private val CIRCUIT_BREAKER_RESET_TIMEOUT = 30.seconds
        private val CIRCUIT_BREAKER_HALF_OPEN_TIMEOUT = 10.seconds
    }

    // Coroutine scope for background operations
    private val mutex = Mutex()

    // Memory cache with timestamp for TTL
    private val flagCache = mutableMapOf<String, CacheEntry>()
    private val cacheTimeout = 5.minutes.inWholeMilliseconds

    // Track last sync timestamp from LaunchDarkly
    private var lastSyncTimestamp: Instant? = null

    // Flow for broadcasting flag changes to observers
    private val _flagChanges = MutableSharedFlow<String>(replay = 0, extraBufferCapacity = 64)

    // Circuit breaker state
    private val circuitBreaker = CircuitBreaker(
        failureThreshold = CIRCUIT_BREAKER_FAILURE_THRESHOLD,
        resetTimeout = CIRCUIT_BREAKER_RESET_TIMEOUT,
        halfOpenTimeout = CIRCUIT_BREAKER_HALF_OPEN_TIMEOUT,
        log = log
    )

    // Deferred client initialization - non-blocking
    private val clientDeferred: Deferred<LDClient> = CompletableDeferred<LDClient>().also { deferred ->
        coroutineScope.launch {
            try {
                val client = initializeClientAsync()
                (deferred as CompletableDeferred).complete(client)
                log.breadcrumb(TAG, "LaunchDarkly client initialized successfully")

                // Prefetch after initialization
                prefetchAllFlags()
            } catch (e: Exception) {
                log.error(TAG, "Failed to initialize LaunchDarkly client", e)
                (deferred as CompletableDeferred).completeExceptionally(e)
            }
        }
    }

    /**
     * Circuit breaker implementation to prevent hammering LaunchDarkly on repeated failures.
     */
    private class CircuitBreaker(
        private val failureThreshold: Int,
        private val resetTimeout: kotlin.time.Duration,
        private val halfOpenTimeout: kotlin.time.Duration,
        private val log: NewmAppLogger
    ) {
        private val failureCount = AtomicInteger(0)
        private val lastFailureTime = AtomicLong(0)
        private val state = java.util.concurrent.atomic.AtomicReference(State.CLOSED)

        enum class State { CLOSED, OPEN, HALF_OPEN }

        fun recordSuccess() {
            failureCount.set(0)
            state.set(State.CLOSED)
        }

        fun recordFailure() {
            val count = failureCount.incrementAndGet()
            lastFailureTime.set(System.currentTimeMillis())

            if (count >= failureThreshold) {
                state.set(State.OPEN)
                log.breadcrumb("CircuitBreaker", "Circuit OPENED after $count failures")
            }
        }

        fun canExecute(): Boolean {
            return when (state.get()) {
                State.CLOSED -> true
                State.OPEN -> {
                    val elapsed = System.currentTimeMillis() - lastFailureTime.get()
                    if (elapsed >= resetTimeout.inWholeMilliseconds) {
                        state.set(State.HALF_OPEN)
                        log.breadcrumb("CircuitBreaker", "Circuit moved to HALF_OPEN")
                        true
                    } else {
                        false
                    }
                }
                State.HALF_OPEN -> {
                    // Allow one request in half-open state
                    val elapsed = System.currentTimeMillis() - lastFailureTime.get()
                    elapsed >= halfOpenTimeout.inWholeMilliseconds
                }
            }
        }

        fun isOpen(): Boolean = state.get() == State.OPEN
    }

    data class CacheEntry(
        val value: Boolean,
        val timestamp: Long
    ) {
        fun isExpired(now: Long, timeout: Long): Boolean = (now - timestamp) > timeout
    }

    /**
     * Initialize LaunchDarkly client asynchronously.
     * This runs on a background thread and doesn't block app startup.
     */
    private suspend fun initializeClientAsync(): LDClient = withContext(Dispatchers.IO) {
        val context = LDContext.builder(ContextKind.DEFAULT, "anonymous")
            .anonymous(true)
            .build()

        val ldConfig: LDConfig = LDConfig.Builder(AutoEnvAttributes.Enabled)
            .mobileKey(sharedBuildConfig.launchDarklyKey)
            .build()

        // Initialize with a shorter timeout since this is async
        val initTimeoutSeconds = 3
        val ldClient = LDClient.init(application, ldConfig, context, initTimeoutSeconds)

        log.breadcrumb(TAG, "LaunchDarkly client initialized with $initTimeoutSeconds second timeout")

        // Register listeners for real-time updates
        registerFlagListeners(ldClient)

        ldClient
    }

    /**
     * Get the client, waiting for initialization if needed.
     * Returns null if initialization failed.
     */
    private suspend fun getClient(): LDClient? {
        return try {
            clientDeferred.await()
        } catch (e: Exception) {
            log.error(TAG, "Failed to get LaunchDarkly client", e)
            null
        }
    }

    /**
     * Eagerly fetch all flags after initialization to populate caches.
     */
    private fun prefetchAllFlags() {
        coroutineScope.launch {
            try {
                log.breadcrumb(TAG, "Prefetching all flags on initialization")

                val client = getClient() ?: return@launch

                FeatureFlags.ALL_FLAGS.forEach { flag ->
                    try {
                        val value = client.boolVariation(flag.key, flag.defaultValue)
                        updateCaches(flag.key, value)
                        log.breadcrumb(TAG, "Prefetched ${flag.key}: $value")
                    } catch (e: Exception) {
                        log.error(TAG, "Error prefetching flag ${flag.key}", e)
                        updateCaches(flag.key, flag.defaultValue)
                    }
                }

                log.breadcrumb(TAG, "All flags prefetched successfully")
            } catch (e: Exception) {
                log.error(TAG, "Error during flag prefetch", e)
            }
        }
    }

    /**
     * Register listeners for real-time flag updates from LaunchDarkly.
     */
    private fun registerFlagListeners(client: LDClient) {
        FeatureFlags.ALL_FLAGS.forEach { flag ->
            client.registerFeatureFlagListener(flag.key) {
                log.breadcrumb(TAG, "Flag ${flag.key} changed remotely")

                coroutineScope.launch {
                    try {
                        val newValue = client.boolVariation(flag.key, flag.defaultValue)
                        updateCaches(flag.key, newValue)
                        _flagChanges.emit(flag.key)
                        log.breadcrumb(TAG, "Flag ${flag.key} updated to $newValue and broadcasted")
                    } catch (e: Exception) {
                        log.error(TAG, "Error updating flag ${flag.key} from listener", e)
                    }
                }
            }
        }
        log.breadcrumb(TAG, "Registered listeners for ${FeatureFlags.ALL_FLAGS.size} flags")
    }

    override fun observeFlagChanges(): Flow<String> {
        return _flagChanges.asSharedFlow()
    }

    override suspend fun getBooleanVariation(featureFlag: FeatureFlag): FlagResult<Boolean> {
        return try {
            withContext(Dispatchers.IO) {
                val now = System.currentTimeMillis()

                // 1. Check memory cache first (fastest)
                mutex.withLock {
                    flagCache[featureFlag.key]?.let { cached ->
                        if (!cached.isExpired(now, cacheTimeout)) {
                            log.breadcrumb(TAG, "Memory cache hit for ${featureFlag.key}: ${cached.value}")
                            return@withContext FlagResult.Success(cached.value)
                        } else {
                            flagCache.remove(featureFlag.key)
                        }
                    }
                }

                // 2. Check disk cache (for offline support)
                val diskCached = getDiskCache(featureFlag.key)
                if (diskCached != null && !diskCached.isExpired(now, cacheTimeout)) {
                    mutex.withLock {
                        flagCache[featureFlag.key] = diskCached
                    }
                    log.breadcrumb(TAG, "Disk cache hit for ${featureFlag.key}: ${diskCached.value}")
                    return@withContext FlagResult.Success(diskCached.value)
                }

                // 3. Check circuit breaker before making remote call
                if (!circuitBreaker.canExecute()) {
                    log.breadcrumb(TAG, "Circuit breaker OPEN, using fallback for ${featureFlag.key}")
                    val fallback = diskCached?.value ?: featureFlag.defaultValue
                    return@withContext FlagResult.Success(fallback)
                }

                // 4. Fetch from LaunchDarkly with retry logic
                val client = getClient()
                if (client == null) {
                    val fallback = diskCached?.value ?: featureFlag.defaultValue
                    return@withContext FlagResult.Error(
                        Exception("LaunchDarkly client not initialized"),
                        fallback
                    )
                }

                val value = fetchWithRetry(client, featureFlag.key, featureFlag.defaultValue)

                // 5. Update caches and circuit breaker
                updateCaches(featureFlag.key, value)
                circuitBreaker.recordSuccess()

                FlagResult.Success(value)
            }
        } catch (e: Exception) {
            log.error(TAG, "Error getting feature flag ${featureFlag.key}", e)
            circuitBreaker.recordFailure()

            // Try to return cached value even if expired
            val lastKnown = mutex.withLock { flagCache[featureFlag.key]?.value }
                ?: getDiskCache(featureFlag.key)?.value

            if (lastKnown != null) {
                log.breadcrumb(TAG, "Using stale cache for ${featureFlag.key}: $lastKnown")
                FlagResult.Success(lastKnown)
            } else {
                FlagResult.Error(e, featureFlag.defaultValue)
            }
        }
    }

    /**
     * Fetch flag value with exponential backoff retry logic.
     * Attempts up to 3 times with increasing delays: 100ms, 400ms, 1600ms
     */
    private suspend fun fetchWithRetry(
        client: LDClient,
        flagKey: String,
        defaultValue: Boolean,
        maxRetries: Int = 3
    ): Boolean {
        var lastException: Exception? = null

        repeat(maxRetries) { attempt ->
            try {
                val value = client.boolVariation(flagKey, defaultValue)
                if (attempt > 0) {
                    log.breadcrumb(TAG, "Successfully fetched $flagKey after ${attempt + 1} attempts")
                }
                return value
            } catch (e: Exception) {
                lastException = e
                if (attempt < maxRetries - 1) {
                    val delayMs = (100 * (4.0.pow(attempt))).toLong()
                    log.breadcrumb(TAG, "Retry $flagKey in ${delayMs}ms (attempt ${attempt + 1})")
                    delay(delayMs)
                }
            }
        }

        throw lastException ?: Exception("Failed to fetch flag $flagKey after $maxRetries attempts")
    }

    /**
     * Update both memory and disk caches atomically.
     */
    private suspend fun updateCaches(flagKey: String, value: Boolean) {
        val now = System.currentTimeMillis()
        val entry = CacheEntry(value, now)

        mutex.withLock {
            flagCache[flagKey] = entry
            lastSyncTimestamp = Clock.System.now()
        }

        saveDiskCache(flagKey, entry)
    }

    /**
     * Load flag value from disk cache.
     */
    private suspend fun getDiskCache(flagKey: String): CacheEntry? {
        return try {
            val value = preferencesStore.getBoolean(DISK_CACHE_PREFIX + flagKey) ?: return null
            val timestamp = preferencesStore.getLong(DISK_CACHE_TIMESTAMP_PREFIX + flagKey) ?: return null
            CacheEntry(value, timestamp)
        } catch (e: Exception) {
            log.error(TAG, "Error reading disk cache for $flagKey", e)
            null
        }
    }

    /**
     * Save flag value to disk cache.
     */
    private suspend fun saveDiskCache(flagKey: String, entry: CacheEntry) {
        try {
            preferencesStore.saveBoolean(DISK_CACHE_PREFIX + flagKey, entry.value)
            preferencesStore.saveLong(DISK_CACHE_TIMESTAMP_PREFIX + flagKey, entry.timestamp)
        } catch (e: Exception) {
            log.error(TAG, "Error saving disk cache for $flagKey", e)
        }
    }

    override suspend fun identifyUser(user: User): FlagResult<Unit> {
        return try {
            val client = getClient() ?: return FlagResult.Error(
                Exception("LaunchDarkly client not initialized")
            )

            val ldContext = LDContext.builder(ContextKind.DEFAULT, user.id)
                .set("email", user.email)
                .build()

            withContext(Dispatchers.IO) {
                client.identify(ldContext).asDeferred().await()

                mutex.withLock {
                    flagCache.clear()
                }

                clearDiskCache()
            }

            log.breadcrumb(TAG, "User identified and caches cleared: ${user.id}")
            FlagResult.Success(Unit)
        } catch (e: Exception) {
            log.error(TAG, "Error identifying user ${user.id}", e)
            FlagResult.Error(e)
        }
    }

    override suspend fun getAllVariations(): FlagResult<Map<String, Boolean>> {
        return try {
            withContext(Dispatchers.IO) {
                val flagValues = mutableMapOf<String, Boolean>()

                FeatureFlags.ALL_FLAGS.forEach { flag ->
                    try {
                        val result = getBooleanVariation(flag)
                        result.fold(
                            onSuccess = { value ->
                                flagValues[flag.key] = value
                            },
                            onError = { _, fallback ->
                                flagValues[flag.key] = fallback ?: flag.defaultValue
                            }
                        )
                    } catch (e: Exception) {
                        log.error(TAG, "Error getting flag ${flag.key}", e)
                        flagValues[flag.key] = flag.defaultValue
                    }
                }

                FlagResult.Success(flagValues)
            }
        } catch (e: Exception) {
            log.error(TAG, "Error getting all flag variations", e)
            val defaultValues = FeatureFlags.ALL_FLAGS.associate { it.key to it.defaultValue }
            FlagResult.Error(e, defaultValues)
        }
    }

    /**
     * Clear all disk cached flags.
     */
    private suspend fun clearDiskCache() {
        FeatureFlags.ALL_FLAGS.forEach { flag ->
            try {
                preferencesStore.deleteValue(DISK_CACHE_PREFIX + flag.key)
                preferencesStore.deleteValue(DISK_CACHE_TIMESTAMP_PREFIX + flag.key)
            } catch (e: Exception) {
                log.error(TAG, "Error clearing disk cache for ${flag.key}", e)
            }
        }
        log.breadcrumb(TAG, "Disk cache cleared for all flags")
    }

    /**
     * Clear all caches (memory and disk).
     */
    suspend fun clearCache() {
        mutex.withLock {
            flagCache.clear()
        }
        clearDiskCache()
        log.breadcrumb(TAG, "All caches cleared (memory and disk)")
    }

    /**
     * Force refresh all flags from LaunchDarkly.
     */
    suspend fun refreshAllFlags(): FlagResult<Unit> {
        return try {
            clearCache()
            getAllVariations()
            log.breadcrumb(TAG, "All flags refreshed from LaunchDarkly")
            FlagResult.Success(Unit)
        } catch (e: Exception) {
            log.error(TAG, "Error refreshing all flags", e)
            FlagResult.Error(e)
        }
    }

    override suspend fun getRemoteValueDirect(featureFlag: FeatureFlag): FlagResult<Boolean> {
        return try {
            withContext(Dispatchers.IO) {
                val client = getClient() ?: return@withContext FlagResult.Error(
                    Exception("LaunchDarkly client not initialized"),
                    featureFlag.defaultValue
                )

                val value = client.boolVariation(featureFlag.key, featureFlag.defaultValue)

                mutex.withLock {
                    lastSyncTimestamp = Clock.System.now()
                }

                log.breadcrumb(TAG, "Direct remote fetch for ${featureFlag.key}: $value")
                FlagResult.Success(value)
            }
        } catch (e: Exception) {
            log.error(TAG, "Error getting remote value for ${featureFlag.key}", e)
            FlagResult.Error(e, featureFlag.defaultValue)
        }
    }

    override suspend fun getLastSyncTimestamp(): Instant? {
        return mutex.withLock {
            lastSyncTimestamp
        }
    }
}

