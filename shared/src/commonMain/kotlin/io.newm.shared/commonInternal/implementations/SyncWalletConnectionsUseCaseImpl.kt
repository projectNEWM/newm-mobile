package io.newm.shared.commonInternal.implementations

import io.newm.shared.commonInternal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.commonInternal.repositories.WalletRepository
import io.newm.shared.commonPublic.models.WalletConnection
import io.newm.shared.commonPublic.models.error.KMMException
import io.newm.shared.commonPublic.usecases.SyncWalletConnectionsUseCase
import org.koin.core.component.KoinComponent
import kotlin.coroutines.cancellation.CancellationException

internal class SyncWalletConnectionsUseCaseImpl(
    private val walletRepository: WalletRepository,
) : SyncWalletConnectionsUseCase,
    KoinComponent {
    @Throws(KMMException::class, CancellationException::class)
    override suspend fun syncWalletConnectionsFromNetworkToDevice(): List<WalletConnection> =
        mapErrorsSuspend {
            walletRepository.syncWalletConnectionsFromNetworkToDB()
        }
}
