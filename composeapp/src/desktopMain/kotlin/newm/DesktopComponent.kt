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
 * Desktop-specific dependency injection component.
 * Provides platform-specific implementations for the desktop (JVM) target.
 */
interface DesktopComponent {

    @Provides
    fun providesFeatureFlagDataSource(): FeatureFlagDataSource {
        return DesktopFeatureFlagManager()
    }
}

/**
 * Desktop implementation of FeatureFlagDataSource.
 * Returns default feature flag values for the desktop platform.
 *
 * Note: Desktop doesn't have LaunchDarkly integration yet.
 * All flags return their default values.
 */
class DesktopFeatureFlagManager : FeatureFlagDataSource {

    override suspend fun getBooleanVariation(featureFlag: FeatureFlag): FlagResult<Boolean> {
        // For desktop, return default values
        return FlagResult.Success(featureFlag.defaultValue)
    }

    override suspend fun identifyUser(user: User): FlagResult<Unit> {
        // No-op for desktop
        return FlagResult.Success(Unit)
    }

    override suspend fun getAllVariations(): FlagResult<Map<String, Boolean>> {
        val defaultValues = FeatureFlags.ALL_FLAGS.associate { it.key to it.defaultValue }
        return FlagResult.Success(defaultValues)
    }

    override fun observeFlagChanges(): Flow<String> {
        // Desktop doesn't support remote flag updates
        return emptyFlow()
    }

    override suspend fun getRemoteValueDirect(featureFlag: FeatureFlag): FlagResult<Boolean> {
        // Desktop doesn't have LaunchDarkly integration
        return FlagResult.Success(featureFlag.defaultValue)
    }

    override suspend fun getLastSyncTimestamp(): Instant? {
        // Desktop doesn't track sync timestamps
        return null
    }
}
