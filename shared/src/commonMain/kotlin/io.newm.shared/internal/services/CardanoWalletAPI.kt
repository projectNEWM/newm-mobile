package io.newm.shared.internal.services

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.newm.shared.di.NetworkClientFactory
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.models.error.KMMException
import org.koin.core.component.KoinComponent
import kotlin.coroutines.cancellation.CancellationException

internal class CardanoWalletAPI(networkClient: NetworkClientFactory) : KoinComponent {

    private val authClient: HttpClient = networkClient.authHttpClient()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getWalletNFTs(xpub: String): List<NFTTrack> =
        authClient.get("/v1/cardano/nft/songs") {
            contentType(ContentType.Application.Json)
            parameter("xpub", xpub)
            parameter("legacy", true)
        }.body()
}
