@file:OptIn(ExperimentalTime::class)

package io.newm.shared.commonPublic.featureflags

import io.newm.shared.commonPublic.models.User
import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime

interface FeatureFlagService {
    // Core business methods
    suspend fun isEnabled(flag: FeatureFlag): FlagResult<Boolean>
    suspend fun setUser(user: User): FlagResult<Unit>
    suspend fun prefetchAllFlags(): FlagResult<Unit>

    // Reactive state observation
    fun observeFlag(flag: FeatureFlag): Flow<Boolean>
    fun observeAllFlags(): Flow<Map<String, Boolean>>

    // Developer tools
    fun getAllFlags(): List<FeatureFlag>
    suspend fun getLocalOverride(flagKey: String): Boolean?
    suspend fun setLocalOverride(flagKey: String, value: Boolean?): FlagResult<Unit>
    suspend fun resetAllOverrides(): FlagResult<Unit>
    suspend fun getEffectiveValue(flag: FeatureFlag): FlagResult<Boolean>
    suspend fun getRemoteValue(flag: FeatureFlag): FlagResult<Boolean>
    suspend fun getLastSyncTimestamp(): kotlin.time.Instant?

    // Debug utilities
    suspend fun exportDebugState(): Map<String, Any>
    fun getEvaluationHistory(): List<FlagEvaluation>
}

// Evaluation tracking for debugging
data class FlagEvaluation(
    val flagKey: String,
    val result: Boolean,
    val source: EvaluationSource,
    val timestamp: kotlin.time.Instant,
    val userId: String?
)

enum class EvaluationSource {
    LOCAL_OVERRIDE,
    REMOTE_SOURCE,
    CACHE,
    FALLBACK
}
