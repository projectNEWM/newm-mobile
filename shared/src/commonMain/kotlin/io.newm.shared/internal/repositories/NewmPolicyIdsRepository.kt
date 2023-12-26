package io.newm.shared.internal.repositories

import com.liftric.kvault.KVault
import io.newm.shared.internal.services.NewmPolicyIdsAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import kotlin.concurrent.Volatile

internal class NewmPolicyIdsRepository(
    private val policyIdsAPI: NewmPolicyIdsAPI,
    private val storage: KVault,
    private val scope: CoroutineScope,

    ) : KoinComponent {

    val initialPolicyList = listOf(
        "46e607b3046a34c95e7c29e47047618dbf5e10de777ba56c590cfd5c",
        "3333c8022c24d2014f02236c082105ebceb73c46c45f94eb99136f92"
    )

    @Volatile
    private var isApiCalled = false

    fun getPolicyIds(): Flow<List<String>> = flow {
        // Emit the locally stored IDs if available
        val storedIds = storage.string(POLICY_IDS_KEY)
            ?: initialPolicyList.joinToString(",")
        if (storedIds.isNotEmpty()) {
            emit(storedIds.split(","))
        }

        // Check if the API has already been called
        if (!isApiCalled) {
            // Fetch from the API, store them, and emit the new list
            try {
                val fetchedIds = policyIdsAPI.getNewmPolicyIds()
                scope.launch {
                    storage.set(POLICY_IDS_KEY, fetchedIds.joinToString(","))
                }
                emit(fetchedIds)

                // Mark the API as called
                isApiCalled = true
            } catch (e: Exception) {
                // Handle the error appropriately
                throw e
            }
        }
    }

    companion object {
        private const val POLICY_IDS_KEY = "policy_ids"
    }
}