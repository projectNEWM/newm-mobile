package io.newm.shared.internal.implementations

import io.newm.shared.internal.implementations.utilities.mapErrors
import io.newm.shared.internal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.internal.repositories.WalletRepository
import io.newm.shared.public.models.WalletConnection
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.GetWalletConnectionsUseCase
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