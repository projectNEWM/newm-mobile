package io.newm.shared.internal.implementations

import io.newm.shared.internal.repositories.CardanoWalletRepository
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.usecases.WalletNFTTracksUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

internal class WalletNFTSongsUseCaseImpl(
    private val cardanoRepository: CardanoWalletRepository,
) : WalletNFTTracksUseCase {

    override fun getAllNFTTracksFlow(): Flow<List<NFTTrack>> {
        return cardanoRepository.getWalletCollectableTracks()
    }

    override suspend fun getAllNFTTracks(): List<NFTTrack> {
        return cardanoRepository.getWalletCollectableTracks().first()
    }

    override fun getAllStreamTokensFlow(): Flow<List<NFTTrack>> {
        return cardanoRepository.getWalletStreamTokens()
    }

    override suspend fun getAllStreamTokens(): List<NFTTrack> {
        return getAllStreamTokensFlow().first()
    }

    override fun getNFTTrack(id: String): NFTTrack? {
        return cardanoRepository.getTrack(id)
    }
}
