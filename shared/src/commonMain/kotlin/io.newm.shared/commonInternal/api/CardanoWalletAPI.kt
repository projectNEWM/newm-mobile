package io.newm.shared.commonInternal.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.newm.shared.commonPublic.models.NFTTrack
import org.koin.core.component.KoinComponent

class CardanoWalletAPI(
    private val authClient: HttpClient,
) : KoinComponent {
    suspend fun getWalletNFTs(): List<NFTTrack> =
        authClient
            .get("/v1/cardano/nft/songs") {
                contentType(ContentType.Application.Json)
                parameter("legacy", true)
            }.body()
}
