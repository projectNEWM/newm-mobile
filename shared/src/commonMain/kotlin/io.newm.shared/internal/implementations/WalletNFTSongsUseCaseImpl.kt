package io.newm.shared.internal.implementations

import io.newm.shared.internal.repositories.CardanoWalletRepository
import io.newm.shared.internal.repositories.ConnectWalletManager
import io.newm.shared.internal.repositories.testdata.MockSongs
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.WalletNFTSongsUseCase
import kotlinx.coroutines.flow.Flow

internal class WalletNFTSongsUseCaseImpl(
    private val cardanoRepository: CardanoWalletRepository,
    private val connectWalletManager: ConnectWalletManager
) : WalletNFTSongsUseCase {

    override fun getAllWalletNFTSongs(): Flow<List<NFTTrack>> = MockSongs.getSongsFlow()
    override suspend fun getWalletNFTs(): List<NFTTrack> {
        val xpub = connectWalletManager.getXpub() ?: throw KMMException("No xpub found")
        return cardanoRepository.getWalletNFTs(xpub)
    }
}
