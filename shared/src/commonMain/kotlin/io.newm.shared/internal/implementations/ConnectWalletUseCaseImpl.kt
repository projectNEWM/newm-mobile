package io.newm.shared.internal.implementations

import io.newm.shared.internal.repositories.CardanoWalletRepository
import io.newm.shared.internal.repositories.ConnectWalletManager
import io.newm.shared.internal.implementations.utilities.mapErrors
import io.newm.shared.public.usecases.ConnectWalletUseCase
import kotlinx.coroutines.flow.Flow
import shared.Notification
import shared.postNotification

internal class ConnectWalletUseCaseImpl(
    private val connectWalletManager: ConnectWalletManager,
    private val cardanoWalletRepository: CardanoWalletRepository
) : ConnectWalletUseCase {
    override fun connect(xpub: String) {
        mapErrors {
            connectWalletManager.connect(xpub)
            postNotification(Notification.walletConnectionStateChanged)
        }
    }

    override fun disconnect() {
        mapErrors {
            connectWalletManager.disconnect()
            cardanoWalletRepository.deleteAllNFTs()
            postNotification(Notification.walletConnectionStateChanged)
        }
    }

    override fun isConnected(): Boolean {
        return mapErrors {
            return@mapErrors connectWalletManager.isConnected()
        }
    }

    override fun isConnectedFlow(): Flow<Boolean> = connectWalletManager.connectionState
}
