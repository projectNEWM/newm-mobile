package io.newm.shared.internal.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.newm.shared.di.NetworkClientFactory
import io.newm.shared.public.models.WalletConnection
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent

internal class NEWMWalletConnectionAPI(private val networkClient: NetworkClientFactory): KoinComponent {
    private val authClient: HttpClient
        get() = networkClient.authHttpClient()

    suspend fun connectWallet(connectionId: String): WalletConnectionNetworkModel =
        authClient.get("/v1/wallet-connections/$connectionId") {
            contentType(ContentType.Application.Json)
        }.body()

    suspend fun getWalletConnections(): List<WalletConnectionNetworkModel> =
        authClient.get("/v1/wallet-connections") {
            contentType(ContentType.Application.Json)
        }.body()

    suspend fun disconnectWallet(connectionId: String) =
        authClient.delete("/v1/wallet-connections/$connectionId") {
            contentType(ContentType.Application.Json)
        }
}

@Serializable
data class WalletConnectionNetworkModel(
    val id: String,
    val createdAt: String,
    val stakeAddress: String,
)

fun WalletConnectionNetworkModel.toWalletConnection() = WalletConnection(
    id = id,
    createdAt = createdAt,
    stakeAddress = stakeAddress,
    name = ""
)