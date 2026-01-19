@file:OptIn(kotlin.time.ExperimentalTime::class)

package newm

import android.app.Application
import com.launchdarkly.sdk.ContextKind
import com.launchdarkly.sdk.LDContext
import com.launchdarkly.sdk.android.LDClient
import com.launchdarkly.sdk.android.LDConfig
import com.launchdarkly.sdk.android.LDConfig.Builder.AutoEnvAttributes
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonPublic.featureflags.FeatureFlag
import io.newm.shared.commonPublic.featureflags.FeatureFlagDataSource
import io.newm.shared.commonPublic.featureflags.FeatureFlags
import io.newm.shared.commonPublic.featureflags.FlagResult
import io.newm.shared.commonPublic.models.User
import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.shared.util.asDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Instant

class AndroidComposeAppFeatureFlagManager(
    private val application: Application,
    private val sharedBuildConfig: NewmSharedBuildConfig,
    private val log: NewmAppLogger,
    private val scope: CoroutineScope,
) : FeatureFlagDataSource {
    private val client: LDClient = buildClient()

    // Simple in-memory cache for flag values
    private val flagCache = mutableMapOf<String, Pair<Boolean, Long>>()
    private val cacheTimeout = 30_000L // 30 seconds
    private val TAG = "AndroidFeatureFlagManager"

    private val _flagChanges = MutableSharedFlow<String>(replay = 0, extraBufferCapacity = 64)

    private fun buildClient(): LDClient {
        val context = LDContext.builder(ContextKind.DEFAULT, "anonymous").anonymous(true).build()

        val ldConfig: LDConfig =
            LDConfig
                .Builder(AutoEnvAttributes.Enabled)
                .mobileKey(sharedBuildConfig.launchDarklyKey)
                .build()

        val ldClient = LDClient.init(application, ldConfig, context, 0)
        registerFlagListeners(ldClient)
        return ldClient
    }

    private fun registerFlagListeners(client: LDClient) {
        FeatureFlags.ALL_FLAGS.forEach { flag ->
            client.registerFeatureFlagListener(flag.key) { scope.launch { _flagChanges.emit(flag.key) } }
        }
    }

    override fun observeFlagChanges(): Flow<String> = _flagChanges.asSharedFlow()

    override suspend fun getBooleanVariation(featureFlag: FeatureFlag): FlagResult<Boolean> {
        return try {
            withContext(Dispatchers.IO) {
                // Check cache first
                val cached = flagCache[featureFlag.key]
                val now = System.currentTimeMillis()

                if (cached != null && (now - cached.second) < cacheTimeout) {
                    log.breadcrumb("FeatureFlag", "Cache hit for ${featureFlag.key}: ${cached.first}")
                    return@withContext FlagResult.Success(cached.first)
                }

                val value = getCachedOrFetch(featureFlag.key, featureFlag.defaultValue)

                // Update cache
                flagCache[featureFlag.key] = value to now

                FlagResult.Success(value)
            }
        } catch (e: Exception) {
            log.error(TAG, "Error getting feature flag ${featureFlag.key}", e)
            FlagResult.Error(e, featureFlag.defaultValue)
        }
    }

    private fun getCachedOrFetch(
        flagKey: String,
        defaultValue: Boolean,
    ): Boolean {
        val cached = flagCache[flagKey]
        val now = System.currentTimeMillis()

        return if (cached != null && (now - cached.second) < cacheTimeout) {
            cached.first
        } else {
            val value = client.boolVariation(flagKey, defaultValue)
            flagCache[flagKey] = value to now
            value
        }
    }

    override suspend fun identifyUser(user: User): FlagResult<Unit> =
        try {
            val ldContext =
                LDContext.builder(ContextKind.DEFAULT, user.id).set("email", user.email).build()

            withContext(Dispatchers.IO) {
                client.identify(ldContext).asDeferred().await()
                // Clear cache when user changes
                flagCache.clear()
            }

            log.breadcrumb("FeatureFlag", "User identified and cache cleared: ${user.id}")
            FlagResult.Success(Unit)
        } catch (e: Exception) {
            log.error(TAG, "Error identifying user ${user.id}", e)
            FlagResult.Error(e)
        }

    override suspend fun getAllVariations(): FlagResult<Map<String, Boolean>> =
        try {
            withContext(Dispatchers.IO) {
                val flagValues = mutableMapOf<String, Boolean>()
                val now = System.currentTimeMillis()

                FeatureFlags.ALL_FLAGS.forEach { flag ->
                    try {
                        val value = getCachedOrFetch(flag.key, flag.defaultValue)
                        flagValues[flag.key] = value
                        flagCache[flag.key] = value to now
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

    override suspend fun getRemoteValueDirect(featureFlag: FeatureFlag): FlagResult<Boolean> =
        try {
            withContext(Dispatchers.IO) {
                val value = client.boolVariation(featureFlag.key, featureFlag.defaultValue)
                FlagResult.Success(value)
            }
        } catch (e: Exception) {
            log.error(TAG, "Error getting feature flag direct ${featureFlag.key}", e)
            FlagResult.Error(e, featureFlag.defaultValue)
        }

    override suspend fun getLastSyncTimestamp(): Instant? = null

    fun clearCache() {
        flagCache.clear()
        log.breadcrumb("FeatureFlag", "Flag cache cleared")
    }
}
