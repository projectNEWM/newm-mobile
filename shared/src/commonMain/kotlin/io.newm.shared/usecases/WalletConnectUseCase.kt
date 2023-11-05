package io.newm.shared.usecases

import io.newm.shared.repositories.WalletConnectManager

interface WalletConnectUseCase {
    fun connect(xpub: String)
    fun disconnect()
    fun isConnected(): Boolean
}

internal class WalletConnectUseCaseImpl(
    private val repository: WalletConnectManager,
) : WalletConnectUseCase {
    override fun connect(xpub: String) {
        repository.connect(xpub)
    }

    override fun disconnect() {
        repository.disconnect()
    }

    override fun isConnected(): Boolean {
        return repository.isConnected()
    }
}
