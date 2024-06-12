package io.newm.shared.internal.services.network

import io.newm.shared.internal.api.CardanoWalletAPI
import io.newm.shared.public.models.NFTTrack
import org.koin.core.component.KoinComponent

internal class NFTNetworkService(
    private val cardanoWalletAPI: CardanoWalletAPI
)  {
    suspend fun getWalletNFTs(): List<NFTTrack> =
        cardanoWalletAPI.getWalletNFTs()
}