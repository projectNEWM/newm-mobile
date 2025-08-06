package io.newm.shared.commonInternal.implementations

import io.newm.shared.commonInternal.implementations.utilities.mapErrors
import io.newm.shared.commonInternal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.commonInternal.repositories.WalletRepository
import io.newm.shared.commonPublic.models.WalletConnection
import io.newm.shared.commonPublic.models.error.KMMException
import io.newm.shared.commonPublic.usecases.GetWalletConnectionsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import kotlin.coroutines.cancellation.CancellationException

internal class GetWalletConnectionsUseCaseImpl(
    private val walletRepository: WalletRepository
) : GetWalletConnectionsUseCase, KoinComponent {

    @Throws(KMMException::class, CancellationException::class)
    override fun getWalletConnectionsFromCacheFlow(): Flow<List<WalletConnection>> {
        return mapErrors {
            walletRepository.getWalletConnectionsCache()
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun getWalletConnectionsFromCache(): List<WalletConnection> {
        return mapErrorsSuspend {
            getWalletConnectionsFromCacheFlow().first()
        }
    }
}