package io.newm.shared.commonInternal.services.network

import io.newm.shared.commonInternal.api.NFTAPI
import io.newm.shared.commonPublic.models.ChainType
import io.newm.shared.commonPublic.models.NFTTrack

private const val TAG = "NFTNetworkService"

class NFTNetworkService(
    private val cardanoWalletAPI: NFTAPI,
) {
    suspend fun getWalletNFTs(): List<NFTTrack> {
        val tracks = cardanoWalletAPI.getWalletNFTs()
        val ethereumCount = tracks.count { it.chainType == ChainType.Ethereum }
        val cardanoCount = tracks.count { it.chainType == ChainType.Cardano }
        val unknownCount = tracks.count { it.chainType == ChainType.Unknown }
        println(
            "$TAG: Fetched ${tracks.size} NFTs from API: $ethereumCount Ethereum, $cardanoCount Cardano, $unknownCount Unknown",
        )
        tracks.take(5).forEach { track ->
            println("$TAG: Sample track: '${track.title}' chainType=${track.chainType}")
        }
        return tracks
    }
}
