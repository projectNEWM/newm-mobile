package io.newm.shared.usecases

import io.newm.shared.models.Song
import io.newm.shared.repositories.testdata.MockSongs
import kotlinx.coroutines.flow.Flow

interface WalletNFTSongsUseCase {
    fun getAllWalletNFTSongs(xPub: String): Flow<List<Song>>
}

internal class WalletNFTSongsUseCaseImpl() : WalletNFTSongsUseCase {
    override fun getAllWalletNFTSongs(xPub: String): Flow<List<Song>> = MockSongs.getSongsFlow()
}
