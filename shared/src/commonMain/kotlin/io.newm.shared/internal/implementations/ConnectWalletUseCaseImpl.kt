package io.newm.shared.internal.implementations

import io.newm.shared.internal.repositories.CardanoWalletRepository
import io.newm.shared.internal.repositories.ConnectWalletManager
import io.newm.shared.public.usecases.ConnectWalletUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ConnectWalletUseCaseImpl(
    private val connectWalletManager: ConnectWalletManager,
    private val cardanoWalletRepository: CardanoWalletRepository
) : ConnectWalletUseCase {
    override fun connect(xpub: String) {
        connectWalletManager.connect(xpub)
    }

    override fun disconnect() {
        connectWalletManager.disconnect()
        cardanoWalletRepository.deleteAllNFTs()
    }

    override fun isConnected(): Boolean {
        return connectWalletManager.isConnected()
    }

    override fun isConnectedFlow(): Flow<Boolean> = flow {
        emit(connectWalletManager.isConnected())
    }
}
