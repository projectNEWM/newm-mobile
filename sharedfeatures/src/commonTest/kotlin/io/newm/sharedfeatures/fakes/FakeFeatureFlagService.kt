package io.newm.sharedfeatures.fakes

import io.newm.shared.commonPublic.featureflags.FeatureFlag
import io.newm.shared.commonPublic.featureflags.FeatureFlagService
import io.newm.shared.commonPublic.featureflags.FlagEvaluation
import io.newm.shared.commonPublic.featureflags.FlagResult
import io.newm.shared.commonPublic.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class FakeFeatureFlagService : FeatureFlagService {
    val flags = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val localOverrides = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val evaluations = mutableListOf<FlagEvaluation>()
    var lastSync: Instant? = null
    var failNextOperation = false

    fun setFlag(
        key: String,
        value: Boolean,
    ) {
        val current = flags.value.toMutableMap()
        current[key] = value
        flags.value = current
    }

    override suspend fun isEnabled(flag: FeatureFlag): FlagResult<Boolean> {
        if (failNextOperation) return FlagResult.Error(Exception("Simulated failure"))
        val value = localOverrides.value[flag.key] ?: flags.value[flag.key] ?: flag.defaultValue
        return FlagResult.Success(value)
    }

    override suspend fun setUser(user: User): FlagResult<Unit> {
        if (failNextOperation) return FlagResult.Error(Exception("Simulated failure"))
        return FlagResult.Success(Unit)
    }

    override suspend fun prefetchAllFlags(): FlagResult<Unit> {
        if (failNextOperation) return FlagResult.Error(Exception("Simulated failure"))
        return FlagResult.Success(Unit)
    }

    override fun observeFlag(flag: FeatureFlag): Flow<Boolean> = flags.map { it[flag.key] ?: flag.defaultValue }

    override fun observeAllFlags(): Flow<Map<String, Boolean>> = flags

    override fun getAllFlags(): List<FeatureFlag> = allAvailableFlags

    // Helper to set all flags available
    var allAvailableFlags: List<FeatureFlag> = emptyList()

    // Override to return injected flags
    fun setAvailableFlags(flags: List<FeatureFlag>) {
        allAvailableFlags = flags
    }

    // We need to implement getAllFlags using the injected list
    fun getAllFlagsImplementation(): List<FeatureFlag> = allAvailableFlags

    override suspend fun getLocalOverride(flagKey: String): Boolean? = localOverrides.value[flagKey]

    override suspend fun setLocalOverride(
        flagKey: String,
        value: Boolean?,
    ): FlagResult<Unit> {
        if (failNextOperation) return FlagResult.Error(Exception("Simulated failure"))
        val current = localOverrides.value.toMutableMap()
        if (value == null) {
            current.remove(flagKey)
        } else {
            current[flagKey] = value
        }
        localOverrides.value = current
        return FlagResult.Success(Unit)
    }

    override suspend fun resetAllOverrides(): FlagResult<Unit> {
        if (failNextOperation) return FlagResult.Error(Exception("Simulated failure"))
        localOverrides.value = emptyMap()
        return FlagResult.Success(Unit)
    }

    override suspend fun getEffectiveValue(flag: FeatureFlag): FlagResult<Boolean> = isEnabled(flag)

    override suspend fun getRemoteValue(flag: FeatureFlag): FlagResult<Boolean> {
        if (failNextOperation) return FlagResult.Error(Exception("Simulated failure"))
        val value = flags.value[flag.key] ?: flag.defaultValue
        return FlagResult.Success(value)
    }

    override suspend fun getLastSyncTimestamp(): Instant? = lastSync

    override suspend fun exportDebugState(): Map<String, Any> {
        if (failNextOperation) throw Exception("Simulated failure")
        return mapOf("flags" to flags.value)
    }

    override fun getEvaluationHistory(): List<FlagEvaluation> = evaluations
}
