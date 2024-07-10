package io.newm.shared.internal.implementations

import io.newm.shared.internal.implementations.utilities.mapErrors
import io.newm.shared.internal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.internal.repositories.NFTRepository
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.WalletNFTTracksUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlin.coroutines.cancellation.CancellationException

internal class WalletNFTTracksUseCaseImpl(
    private val nftRepository: NFTRepository,
) : WalletNFTTracksUseCase {

    override val walletSynced: Flow<Boolean> = nftRepository.isSynced

    override fun getAllCollectableTracksFlow(): Flow<List<NFTTrack>> {
        return nftRepository.getAllCollectableTracksFlow()
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun getAllCollectableTracks(): List<NFTTrack> {
        return mapErrorsSuspend {
            return@mapErrorsSuspend nftRepository.getAllCollectableTracksFlow().first()
        }
    }

    override fun getAllStreamTokensFlow(): Flow<List<NFTTrack>> {
        return nftRepository.getAllStreamTokensFlow()
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun getAllStreamTokens(): List<NFTTrack> {
        return mapErrorsSuspend {
            return@mapErrorsSuspend nftRepository.getAllStreamTokensFlow().first()
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override fun getNFTTrack(id: String): NFTTrack? {
        return mapErrors {
            return@mapErrors nftRepository.getTrack(id)
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun refresh() {
        mapErrorsSuspend {
            nftRepository.syncNFTTracksFromNetworkToDevice()
        }
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
