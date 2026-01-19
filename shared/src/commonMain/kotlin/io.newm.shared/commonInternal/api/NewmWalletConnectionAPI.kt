package io.newm.shared.commonInternal.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.newm.shared.commonPublic.models.WalletConnection
import org.koin.core.component.KoinComponent

class NEWMWalletConnectionAPI(
    private val authClient: HttpClient,
) : KoinComponent {
    suspend fun connectWallet(connectionId: String): WalletConnection =
        authClient
            .get("/v1/wallet-connections/$connectionId") { contentType(ContentType.Application.Json) }
            .body()

    suspend fun getWalletConnections(): List<WalletConnection> =
        authClient.get("/v1/wallet-connections") { contentType(ContentType.Application.Json) }.body()

    suspend fun disconnectWallet(connectionId: String) =
        authClient.delete("/v1/wallet-connections/$connectionId") {
            contentType(ContentType.Application.Json)
        }
}
