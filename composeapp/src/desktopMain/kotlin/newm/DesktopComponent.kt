package newm

import io.newm.shared.commonPublic.featureflags.FeatureFlag
import io.newm.shared.commonPublic.featureflags.FeatureFlagDataSource
import io.newm.shared.commonPublic.featureflags.FeatureFlags
import io.newm.shared.commonPublic.featureflags.FlagResult
import io.newm.shared.commonPublic.models.User
import me.tatarka.inject.annotations.Provides

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
 */
class DesktopFeatureFlagManager : FeatureFlagDataSource {

    override suspend fun getBooleanVariation(featureFlag: FeatureFlag): FlagResult<Boolean> {
        // For desktop, return default values for now
        return FlagResult.Success(featureFlag.defaultValue)
    }

    override suspend fun identifyUser(user: User): FlagResult<Unit> {
        return FlagResult.Success(Unit)
    }

    override suspend fun getAllVariations(): FlagResult<Map<String, Boolean>> {
        val defaultValues = FeatureFlags.ALL_FLAGS.associate { it.key to it.defaultValue }
        return FlagResult.Success(defaultValues)
    }
}