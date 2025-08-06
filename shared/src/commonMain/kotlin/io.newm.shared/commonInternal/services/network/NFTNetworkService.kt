package io.newm.shared.commonInternal.services.network

import io.newm.shared.commonInternal.api.CardanoWalletAPI
import io.newm.shared.commonPublic.models.NFTTrack

class NFTNetworkService(
    private val cardanoWalletAPI: CardanoWalletAPI
)  {
    suspend fun getWalletNFTs(): List<NFTTrack> =
        cardanoWalletAPI.getWalletNFTs()
}