package io.newm.shared.commonInternal.implementations

import io.newm.shared.commonInternal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.commonInternal.repositories.NFTRepository
import io.newm.shared.commonPublic.models.NFTTrack
import io.newm.shared.commonPublic.models.error.KMMException
import io.newm.shared.commonPublic.usecases.WalletNFTTracksUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlin.coroutines.cancellation.CancellationException

internal class WalletNFTTracksUseCaseImpl(
    private val nftRepository: NFTRepository,
) : WalletNFTTracksUseCase {
    override val walletSynced: Flow<Boolean> = nftRepository.isSynced

    override fun getAllCollectableTracksFlow(): Flow<List<NFTTrack>> = nftRepository.getAllCollectableTracksFlow()

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun getAllCollectableTracks(): List<NFTTrack> {
        return mapErrorsSuspend {
            return@mapErrorsSuspend nftRepository.getAllCollectableTracks()
        }
    }

    override fun getAllStreamTokensFlow(): Flow<List<NFTTrack>> = nftRepository.getAllStreamTokensFlow()

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun getAllStreamTokens(): List<NFTTrack> {
        return mapErrorsSuspend {
            return@mapErrorsSuspend nftRepository.getAllStreamTokens()
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun refresh() {
        mapErrorsSuspend { nftRepository.syncNFTTracksFromNetworkToDevice() }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun getAllTracksFlow(): Flow<List<NFTTrack>> {
        return mapErrorsSuspend {
            return@mapErrorsSuspend nftRepository.getAll()
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun getAllTracks(): List<NFTTrack> {
        return mapErrorsSuspend {
            return@mapErrorsSuspend getAllTracksFlow().first()
        }
    }
}
