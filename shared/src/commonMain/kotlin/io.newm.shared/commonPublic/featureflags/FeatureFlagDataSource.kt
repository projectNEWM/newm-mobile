@file:OptIn(kotlin.time.ExperimentalTime::class)

package io.newm.shared.commonPublic.featureflags

import io.newm.shared.commonPublic.models.User
import kotlinx.coroutines.flow.Flow

interface FeatureFlagDataSource {
    suspend fun getBooleanVariation(featureFlag: FeatureFlag): FlagResult<Boolean>
    suspend fun identifyUser(user: User): FlagResult<Unit>
    suspend fun getAllVariations(): FlagResult<Map<String, Boolean>>

    /**
     * Flow that emits when any flag changes remotely.
     * Emits the flag key that changed.
     */
    fun observeFlagChanges(): Flow<String>

    /**
     * Get the raw remote value from LaunchDarkly, bypassing cache and overrides.
     * This is useful for debugging to see what LaunchDarkly actually returns.
     */
    suspend fun getRemoteValueDirect(featureFlag: FeatureFlag): FlagResult<Boolean>

    /**
     * Get the timestamp when LaunchDarkly was last synced (any flag fetch).
     */
    suspend fun getLastSyncTimestamp(): kotlin.time.Instant?
}