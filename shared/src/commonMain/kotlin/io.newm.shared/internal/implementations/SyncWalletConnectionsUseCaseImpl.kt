package io.newm.shared.internal.implementations

import io.newm.shared.internal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.internal.repositories.WalletRepository
import io.newm.shared.public.models.WalletConnection
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.SyncWalletConnectionsUseCase
import org.koin.core.component.KoinComponent
import kotlin.coroutines.cancellation.CancellationException

internal class SyncWalletConnectionsUseCaseImpl(
    private val walletRepository: WalletRepository
) : SyncWalletConnectionsUseCase, KoinComponent {

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun syncWalletConnectionsFromNetworkToDevice(): List<WalletConnection> {
        return mapErrorsSuspend {
            walletRepository.syncWalletConnectionsFromNetworkToDB()
        }
    }
}