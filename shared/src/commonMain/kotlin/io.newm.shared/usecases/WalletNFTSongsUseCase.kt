package io.newm.shared.usecases

import co.touchlab.kermit.Logger
import io.newm.shared.login.repository.LogInRepository
import io.newm.shared.models.Song
import io.newm.shared.repositories.CardanoWalletRepository
import io.newm.shared.repositories.NFTTrack
import io.newm.shared.repositories.testdata.MockSongs
import io.newm.shared.services.LedgerAssetMetadata
import io.newm.shared.services.UserAPI
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.inject

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
