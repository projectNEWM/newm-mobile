package io.newm.shared.commonPublic.featureflags

import io.newm.shared.commonPublic.models.User

interface FeatureFlagDataSource {
    suspend fun getBooleanVariation(featureFlag: FeatureFlag): FlagResult<Boolean>
    suspend fun identifyUser(user: User): FlagResult<Unit>
    suspend fun getAllVariations(): FlagResult<Map<String, Boolean>>
}