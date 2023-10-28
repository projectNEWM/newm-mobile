package io.newm.shared.usecases

import io.newm.shared.models.Song
import io.newm.shared.repositories.CardanoWalletRepository
import io.newm.shared.repositories.NFTTrack
import io.newm.shared.repositories.testdata.MockSongs
import kotlinx.coroutines.flow.Flow

interface WalletNFTSongsUseCase {
    fun getAllWalletNFTSongs(xPub: String): Flow<List<Song>>

    suspend fun getWalletNFTs(xpub: String): List<NFTTrack>
}

internal class WalletNFTSongsUseCaseImpl(private val repository: CardanoWalletRepository) : WalletNFTSongsUseCase {

    override fun getAllWalletNFTSongs(xPub: String): Flow<List<Song>> = MockSongs.getSongsFlow()
    override suspend fun getWalletNFTs(xpub: String): List<NFTTrack> {
        return repository.getWalletNFTs(xpub)
    }
}
