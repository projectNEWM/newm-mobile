package io.newm.shared.usecases

import io.newm.shared.models.Song
import io.newm.shared.repositories.CardanoWalletRepository
import io.newm.shared.repositories.NFTTrack
import io.newm.shared.repositories.WalletConnectManager
import io.newm.shared.repositories.testdata.MockSongs
import kotlinx.coroutines.flow.Flow

interface WalletNFTSongsUseCase {
    fun getAllWalletNFTSongs(xPub: String): Flow<List<Song>>
    suspend fun getWalletNFTs(): List<NFTTrack>
}

internal class WalletNFTSongsUseCaseImpl(
    private val cardanoRepository: CardanoWalletRepository,
    private val walletConnectManager: WalletConnectManager
) : WalletNFTSongsUseCase {

    override fun getAllWalletNFTSongs(xPub: String): Flow<List<Song>> = MockSongs.getSongsFlow()
    override suspend fun getWalletNFTs(): List<NFTTrack> {
        val xpub = walletConnectManager.getXpub() ?: throw IllegalStateException("No xpub found")
        return cardanoRepository.getWalletNFTs(xpub)
    }
}
