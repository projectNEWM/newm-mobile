package io.newm.shared.internal.services.network

import io.newm.shared.internal.api.NewmPolicyIdsAPI
import org.koin.core.component.KoinComponent

internal class NewmPolicyIdsNetworkService(
    private val policyIdsAPI: NewmPolicyIdsAPI
)  {
    suspend fun fetchPolicyIds(): List<String> = policyIdsAPI.getNewmPolicyIds()
}