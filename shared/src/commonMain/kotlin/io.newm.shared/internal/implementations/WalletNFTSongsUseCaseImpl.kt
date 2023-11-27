package io.newm.shared.internal.implementations

import io.newm.shared.internal.repositories.CardanoWalletRepository
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.WalletNFTTracksUseCase
import kotlinx.coroutines.flow.Flow

internal class WalletNFTSongsUseCaseImpl(
    private val cardanoRepository: CardanoWalletRepository,
) : WalletNFTTracksUseCase {

    override fun getAllNFTTracksFlow(): Flow<List<NFTTrack>> {
        return cardanoRepository.getWalletNFTsFlow()
    }

    override suspend fun getAllNFTTracks(): List<NFTTrack> {
        return cardanoRepository.getWalletNFTs()
    }

    override fun getNFTTrack(id: String): NFTTrack? {
        return cardanoRepository.getTrack(id)
    }

}
