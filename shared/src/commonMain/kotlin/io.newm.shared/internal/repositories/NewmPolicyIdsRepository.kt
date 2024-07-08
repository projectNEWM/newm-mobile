package io.newm.shared.internal.repositories

import io.newm.shared.NewmAppLogger
import io.newm.shared.internal.services.cache.NewmPolicyIdsCacheService
import io.newm.shared.internal.services.network.NewmPolicyIdsNetworkService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.concurrent.Volatile

internal class NewmPolicyIdsRepository(
    private val networkService: NewmPolicyIdsNetworkService,
    private val cacheService: NewmPolicyIdsCacheService,
    private val scope: CoroutineScope,
    private val logger: NewmAppLogger
) {
    @Volatile
    private var isApiCalled = false

    fun getPolicyIds(): Flow<List<String>> = flow {
        // Emit the locally stored IDs if available
        val storedIds = cacheService.getPolicyIds().firstOrNull()
        if (!storedIds.isNullOrEmpty()) {
            emit(storedIds)
        }

        // Check if the API has already been called
        if (!isApiCalled) {
            // Fetch from the API, store them, and emit the new list
            try {
                val fetchedIds = networkService.fetchPolicyIds()
                scope.launch {
                    cacheService.savePolicyIds(fetchedIds)
                }
                emit(fetchedIds)

                // Mark the API as called
                isApiCalled = true
            } catch (e: Exception) {
                logger.error("NewmPolicyIdsRepository", "Error fetching policy IDs from network", e)
                emit(emptyList())
            }
        }
    }
}