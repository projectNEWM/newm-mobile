package io.newm.shared.internal.implementations

import io.newm.shared.internal.repositories.CardanoWalletRepository
import io.newm.shared.internal.implementations.utilities.mapErrors
import io.newm.shared.public.models.WalletConnection
import io.newm.shared.public.usecases.ConnectWalletUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import shared.Notification
import shared.postNotification

internal class ConnectWalletUseCaseImpl(
    private val cardanoWalletRepository: CardanoWalletRepository
) : ConnectWalletUseCase {
    override fun connect(walletConnectionId: String) {
        mapErrors {
            cardanoWalletRepository.connectWallet(walletConnectionId)
            postNotification(Notification.walletConnectionStateChanged)
        }
    }

    override fun disconnect(walletConnectionId: String?) {
        mapErrors {
            cardanoWalletRepository.deleteAllNFTs()
            postNotification(Notification.walletConnectionStateChanged)
            cardanoWalletRepository.disconnectWallet(walletConnectionId)
        }
    }

    override fun hasWalletConnections(): Flow<Boolean> {
        return getWalletConnections().map { connections ->
            connections.isNotEmpty()
        }
    }
    override fun getWalletConnections(): Flow<List<WalletConnection>> {
        return mapErrors {
            cardanoWalletRepository.getWalletConnections()
        }
    }
}
