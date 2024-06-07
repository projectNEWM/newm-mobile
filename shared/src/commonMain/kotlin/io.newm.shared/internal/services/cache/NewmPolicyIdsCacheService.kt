package io.newm.shared.internal.services.cache

import io.newm.shared.internal.db.PreferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

internal class NewmPolicyIdsCacheService(
    private val storage: PreferencesDataStore
)  {
    fun getPolicyIds(): Flow<List<String>> = flow {
        val storedIds = storage.getString(POLICY_IDS_KEY)
        if (storedIds != null) {
            emit(storedIds.split(","))
        } else {
            emit(emptyList())
        }
    }

    fun savePolicyIds(policyIds: List<String>) {
        storage.saveString(POLICY_IDS_KEY, policyIds.joinToString(","))
    }

    companion object {
        private const val POLICY_IDS_KEY = "policy_ids"
    }
}