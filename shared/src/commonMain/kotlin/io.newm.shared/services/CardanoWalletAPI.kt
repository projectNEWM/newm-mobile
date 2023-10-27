package io.newm.shared.services

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.newm.shared.di.NetworkClientFactory
import io.newm.shared.login.repository.KMMException
import org.koin.core.component.KoinComponent
import kotlin.coroutines.cancellation.CancellationException

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal class CardanoWalletAPI(networkClient: NetworkClientFactory) : KoinComponent {

    private val authClient: HttpClient = networkClient.authHttpClient()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getWalletNFTs(xpub: String): List<List<LedgerAssetMetadata>> =
        authClient.get("/v1/cardano/nfts") {
            contentType(ContentType.Application.Json)
            parameter("xpub", xpub)
        }.body()
}


@Serializable
data class LedgerAssetMetadata(
    @SerialName("keyType")
    val keyType: String,
    @SerialName("key")
    val key: String,
    @SerialName("valueType")
    val valueType: String,
    @SerialName("value")
    val value: String,
    @SerialName("nestLevel")
    val nestLevel: Int,
    @SerialName("children")
    val children: List<LedgerAssetMetadata>,
)
