package io.newm.shared.internal.implementations

import io.newm.shared.internal.implementations.utilities.mapErrors
import io.newm.shared.internal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.internal.repositories.CardanoWalletRepository
import io.newm.shared.public.models.WalletConnection
import io.newm.shared.public.usecases.ConnectWalletUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import shared.Notification
import shared.postNotification

internal class ConnectWalletUseCaseImpl(
    private val cardanoWalletRepository: CardanoWalletRepository
) : ConnectWalletUseCase {
    override suspend fun connect(walletConnectionId: String) {
        mapErrors {
            postNotification(Notification.walletConnectionStateChanged)
            cardanoWalletRepository.connectWallet(walletConnectionId)
        }
    }

    override suspend fun disconnect(walletConnectionId: String?) {
        mapErrors {
            //TODO: This will need to be updated to handle disconnecting from a specific wallet connection
            cardanoWalletRepository.deleteAllNFTs()
            postNotification(Notification.walletConnectionStateChanged)
            cardanoWalletRepository.disconnectWallet(walletConnectionId)
        }
    }

    override fun hasWalletConnectionsFlow(): Flow<Boolean> {
        return getWalletConnectionsFlow().map { connections ->
            connections.isNotEmpty()
        }
    }

    override fun getWalletConnectionsFlow(): Flow<List<WalletConnection>> {
        return mapErrors {
            cardanoWalletRepository.getWalletConnections()
        }
    }

    override suspend fun hasWalletConnections(): Boolean {
        return mapErrorsSuspend {
            return@mapErrorsSuspend hasWalletConnectionsFlow().first()
        }
    }

    override suspend fun getWalletConnections(): List<WalletConnection> {
        return mapErrorsSuspend {
            return@mapErrorsSuspend getWalletConnectionsFlow().first()
        }
    }
}
