package io.newm.shared.internal.usecases

import io.newm.shared.internal.repositories.CardanoWalletRepository
import io.newm.shared.internal.usecases.utilities.mapErrors
import io.newm.shared.internal.usecases.utilities.mapErrorsSuspend
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.WalletNFTTracksUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlin.coroutines.cancellation.CancellationException

internal class WalletNFTTracksUseCaseImpl(
    private val cardanoRepository: CardanoWalletRepository,
) : WalletNFTTracksUseCase {

    override fun getAllNFTTracksFlow(): Flow<List<NFTTrack>> {
        return cardanoRepository.getWalletCollectableTracks()
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun getAllNFTTracks(): List<NFTTrack> {
        return mapErrorsSuspend {
            return@mapErrorsSuspend cardanoRepository.getWalletCollectableTracks().first()
        }
    }

    override fun getAllStreamTokensFlow(): Flow<List<NFTTrack>> {
        return cardanoRepository.getWalletStreamTokens()
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun getAllStreamTokens(): List<NFTTrack> {
        return mapErrorsSuspend {
            return@mapErrorsSuspend getAllStreamTokensFlow().first()
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override fun getNFTTrack(id: String): NFTTrack? {
        return mapErrors {
            return@mapErrors cardanoRepository.getTrack(id)
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun refresh() {
        mapErrorsSuspend {
            cardanoRepository.fetchNFTTracksFromNetwork()
        }
    }
}