package io.newm.shared.internal.implementations

import io.newm.shared.internal.repositories.CardanoWalletRepository
import io.newm.shared.internal.repositories.ConnectWalletManager
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.WalletNFTTracksUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class WalletNFTSongsUseCaseImpl(
    private val cardanoRepository: CardanoWalletRepository,
    private val connectWalletManager: ConnectWalletManager
) : WalletNFTTracksUseCase {

    override fun getAllNFTTracksFlow(): Flow<List<NFTTrack>> {
        return flow {
            emit(getAllNFTTracks())
        }
    }
    override suspend fun getAllNFTTracks(): List<NFTTrack> {
        val xpub = connectWalletManager.getXpub() ?: throw KMMException("No xpub found")
        return cardanoRepository.getWalletNFTs(xpub)
    }
}
