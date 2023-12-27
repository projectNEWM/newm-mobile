package io.newm.shared.internal.services

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.newm.shared.di.NetworkClientFactory
import io.newm.shared.public.models.error.KMMException
import org.koin.core.component.KoinComponent
import kotlin.coroutines.cancellation.CancellationException

internal class NewmPolicyIdsAPI(networkClient: NetworkClientFactory) : KoinComponent {
    private val httpClient: HttpClient = networkClient.httpClient()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getNewmPolicyIds(): List<String> =
        httpClient.get("/contents/stream-token-policy-ids.json").body()

}