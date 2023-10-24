package io.newm.shared.repositories

import co.touchlab.kermit.Logger
import io.newm.shared.services.CardanoWalletAPI
import io.newm.shared.services.LedgerAssetMetadata
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal interface CardanoWalletRepository {
    suspend fun getWalletNFTs(xpub: String): List<LedgerAssetMetadata>
}

internal class CardanoWalletRepositoryImpl : KoinComponent, CardanoWalletRepository {

    private val service: CardanoWalletAPI by inject()
    private val logger = Logger.withTag("NewmKMM-CardanoWalletRepository")

    override suspend fun getWalletNFTs(xpub: String): List<LedgerAssetMetadata> {
        logger.d { "getWalletNFTs() w/ xpub key: $xpub" }
        val nfts = service.getWalletNFTs(xpub)
        logger.d { "Result : ${nfts.first()}" }
        return nfts.first()
    }
}

