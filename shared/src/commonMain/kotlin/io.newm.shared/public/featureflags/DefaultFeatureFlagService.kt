package io.newm.shared.public.featureflags

import io.newm.shared.NewmAppLogger
import io.newm.shared.internal.db.PreferencesDataStore
import io.newm.shared.public.models.User

class DefaultFeatureFlagService(
    private val dataSource: FeatureFlagDataSource,
    private val preferencesStore: PreferencesDataStore,
    private val logger: NewmAppLogger
) : FeatureFlagService{
    private val overridePrefix = "dev_override_"

    override suspend fun setUser(user: User) {
        logger.breadcrumb("FeatureFlagService", "Setting user: ${user.id}")
        dataSource.identifyUser(user)
    }

    override fun isEnabled(flag: FeatureFlag, default: Boolean): Boolean {
        val localOverride = getLocalOverride(flag.key)
        if (localOverride != null) {
            logger.breadcrumb("FeatureFlagService", "Override for ${flag.displayName}: $localOverride")
            return localOverride
        }

        val advancedAccessFlag = FeatureFlags.AdvancedAccess
        if (flag.key != advancedAccessFlag.key) {
            val aaLocalOverride = getLocalOverride(advancedAccessFlag.key)
            val isAdvancedAccessEffectivelyEnabled = if (aaLocalOverride != null) {
                aaLocalOverride
            } else {
                dataSource.getBooleanVariation(advancedAccessFlag)
            }

            if (isAdvancedAccessEffectivelyEnabled) {
                logger.breadcrumb("FeatureFlagService", "${flag.displayName} enabled due to Advanced Access rule.")
                return true
            }
        }
        return dataSource.getBooleanVariation(flag)
    }

    override fun getAllDeveloperFlags(): List<FeatureFlag> {
        return FeatureFlags.ALL_FLAGS
    }

    override fun getRawBooleanVariationFromSource(flagKey: FeatureFlag): Boolean {
        return dataSource.getBooleanVariation(flagKey)
    }

    override fun getLocalOverride(flagKey: String): Boolean? {
        return preferencesStore.getBoolean(overridePrefix + flagKey)
    }

    override fun setLocalOverride(flagKey: String, value: Boolean?) {
        val prefKey = overridePrefix + flagKey
        if (value == null) {
            preferencesStore.deleteValue(prefKey)
            logger.breadcrumb("FeatureFlagService", "Override cleared for key: $flagKey")
        } else {
            preferencesStore.saveBoolean(prefKey, value)
            logger.breadcrumb("FeatureFlagService", "Override set for key: $flagKey to $value")
        }
    }

    override fun resetAllLocalOverrides() {
        logger.breadcrumb("FeatureFlagService", "Resetting all local feature flag overrides.")
        FeatureFlags.ALL_FLAGS.forEach { flag ->
            val prefKey = overridePrefix + flag.key
            preferencesStore.deleteValue(prefKey)
        }
    }

    override fun getDevMenuEffectiveValue(flag: FeatureFlag): Boolean {
        val localOverride = getLocalOverride(flag.key)
        if (localOverride != null) {
            return localOverride
        }
        return dataSource.getBooleanVariation(flag)
    }
}