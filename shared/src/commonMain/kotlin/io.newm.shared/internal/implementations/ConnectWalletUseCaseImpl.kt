
package io.newm.shared.internal.implementations

import io.newm.shared.internal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.internal.repositories.WalletRepository
import io.newm.shared.public.models.WalletConnection
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.ConnectWalletUseCase
import io.newm.shared.public.usecases.SyncWalletConnectionsUseCase
import org.koin.core.component.KoinComponent
import shared.Notification
import shared.postNotification
import kotlin.coroutines.cancellation.CancellationException

internal class ConnectWalletUseCaseImpl(
    private val walletRepository: WalletRepository,
    private val syncWalletConnectionsUseCase: SyncWalletConnectionsUseCase,
) : ConnectWalletUseCase {

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun connect(walletConnectionId: String): WalletConnection? {
        return mapErrorsSuspend {
            val walletConnection = walletRepository.connectWallet(walletConnectionId)
            postNotification(Notification.walletConnectionStateChanged)
            // Sync wallet connections after connecting
            syncWalletConnectionsUseCase.syncWalletConnectionsFromNetworkToDevice()
            walletConnection
        }
    }
}
