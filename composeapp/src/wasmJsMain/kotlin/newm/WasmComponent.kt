@file:OptIn(kotlin.time.ExperimentalTime::class)

package newm

import io.newm.shared.commonPublic.featureflags.FeatureFlag
import io.newm.shared.commonPublic.featureflags.FeatureFlagDataSource
import io.newm.shared.commonPublic.featureflags.FeatureFlags
import io.newm.shared.commonPublic.featureflags.FlagResult
import io.newm.shared.commonPublic.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import me.tatarka.inject.annotations.Provides
import kotlin.time.Instant

/**
 * WebAssembly-specific dependency injection component. Provides platform-specific implementations
 * for the wasmJs (web) target.
 */
interface WasmComponent {
    @Provides fun providesFeatureFlagDataSource(): FeatureFlagDataSource = WasmFeatureFlagManager()
}

/**
 * WebAssembly implementation of FeatureFlagDataSource. Returns default feature flag values for the
 * web platform.
 *
 * Note: WebAssembly doesn't have LaunchDarkly integration yet. All flags return their default
 * values. Consider using LaunchDarkly's JavaScript SDK via interop in the future.
 */
class WasmFeatureFlagManager : FeatureFlagDataSource {
    override suspend fun getBooleanVariation(featureFlag: FeatureFlag): FlagResult<Boolean> {
        // For wasmJs/web, return default values
        return FlagResult.Success(featureFlag.defaultValue)
    }

    override suspend fun identifyUser(user: User): FlagResult<Unit> {
        // No-op for web
        return FlagResult.Success(Unit)
    }

    override suspend fun getAllVariations(): FlagResult<Map<String, Boolean>> {
        val defaultValues = FeatureFlags.ALL_FLAGS.associate { it.key to it.defaultValue }
        return FlagResult.Success(defaultValues)
    }

    override fun observeFlagChanges(): Flow<String> {
        // WebAssembly doesn't support remote flag updates
        return emptyFlow()
    }

    override suspend fun getRemoteValueDirect(featureFlag: FeatureFlag): FlagResult<Boolean> {
        // WebAssembly doesn't have LaunchDarkly integration
        return FlagResult.Success(featureFlag.defaultValue)
    }

    override suspend fun getLastSyncTimestamp(): Instant? {
        // WebAssembly doesn't track sync timestamps
        return null
    }
}
