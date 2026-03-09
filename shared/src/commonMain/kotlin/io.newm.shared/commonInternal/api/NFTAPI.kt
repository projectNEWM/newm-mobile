package io.newm.shared.commonInternal.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.newm.shared.commonInternal.api.models.NFTSongResponse
import io.newm.shared.commonInternal.api.models.toDomainOrNull
import io.newm.shared.commonPublic.models.NFTTrack
import org.koin.core.component.KoinComponent

class NFTAPI(
    private val authClient: HttpClient,
) : KoinComponent {
    suspend fun getWalletNFTs(): List<NFTTrack> =
        authClient
            .get("/v1/nft/songs") { contentType(ContentType.Application.Json) }
            .body<List<NFTSongResponse>>()
            .mapNotNull(NFTSongResponse::toDomainOrNull)
}
