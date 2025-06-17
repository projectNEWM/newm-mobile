package io.newm.shared.public.featureflags

import io.newm.shared.public.models.User

interface FeatureFlagDataSource {
    fun getBooleanVariation(featureFlag: FeatureFlag): Boolean
    suspend fun identifyUser(user: User)
}