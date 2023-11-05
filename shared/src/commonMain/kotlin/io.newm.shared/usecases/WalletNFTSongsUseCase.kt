package io.newm.shared.usecases

import io.newm.shared.login.repository.KMMException
import io.newm.shared.models.Song
import io.newm.shared.repositories.CardanoWalletRepository
import io.newm.shared.repositories.NFTTrack
import io.newm.shared.repositories.WalletConnectManager
import io.newm.shared.repositories.testdata.MockSongs
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException

interface WalletNFTSongsUseCase {
    fun getAllWalletNFTSongs(): Flow<List<Song>>

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getWalletNFTs(): List<NFTTrack>
}

internal class WalletNFTSongsUseCaseImpl(
    private val cardanoRepository: CardanoWalletRepository,
    private val walletConnectManager: WalletConnectManager
) : WalletNFTSongsUseCase {

    override fun getAllWalletNFTSongs(): Flow<List<Song>> = MockSongs.getSongsFlow()
    override suspend fun getWalletNFTs(): List<NFTTrack> {
        val xpub = walletConnectManager.getXpub() ?: throw KMMException("No xpub found")
        return cardanoRepository.getWalletNFTs(xpub)
    }
}
