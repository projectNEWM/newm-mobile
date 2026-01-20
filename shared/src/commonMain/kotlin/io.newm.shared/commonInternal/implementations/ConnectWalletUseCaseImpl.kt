package io.newm.shared.commonInternal.implementations

import io.newm.shared.commonInternal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.commonInternal.repositories.WalletRepository
import io.newm.shared.commonPublic.models.WalletConnection
import io.newm.shared.commonPublic.models.error.KMMException
import io.newm.shared.commonPublic.usecases.ConnectWalletUseCase
import io.newm.shared.commonPublic.usecases.SyncWalletConnectionsUseCase
import shared.Notification
import shared.postNotification
import kotlin.coroutines.cancellation.CancellationException

internal class ConnectWalletUseCaseImpl(
    private val walletRepository: WalletRepository,
    private val syncWalletConnectionsUseCase: SyncWalletConnectionsUseCase,
) : ConnectWalletUseCase {
    @Throws(KMMException::class, CancellationException::class)
    override suspend fun connect(walletConnectionId: String): WalletConnection? =
        mapErrorsSuspend {
            val walletConnection = walletRepository.connectWallet(walletConnectionId)
            postNotification(Notification.WALLET_CONNECTION_STATE_CHANGED)
            // Sync wallet connections after connecting
            syncWalletConnectionsUseCase.syncWalletConnectionsFromNetworkToDevice()
            walletConnection
        }
}
