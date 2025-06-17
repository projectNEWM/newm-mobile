package io.newm.shared.public.featureflags

import io.newm.shared.public.models.User

interface FeatureFlagService {
    // Business methods
    fun isEnabled(flag: FeatureFlag, default: Boolean = flag.defaultUiValue): Boolean
    suspend fun setUser(user: User)

    // --- Methods for Dev Menu & Override Management ---
    fun getAllDeveloperFlags(): List<FeatureFlag>

    /** Gets the raw value directly from the underlying data source (e.g., LaunchDarkly), bypassing local overrides. */
    fun getRawBooleanVariationFromSource(flagKey: FeatureFlag): Boolean

    fun getLocalOverride(flagKey: String): Boolean?
    fun setLocalOverride(flagKey: String, value: Boolean?)
    fun resetAllLocalOverrides()

    /** Gets the effective value to be displayed in the Dev Menu (considers override first, then raw source value). */
    fun getDevMenuEffectiveValue(flag: FeatureFlag): Boolean
}