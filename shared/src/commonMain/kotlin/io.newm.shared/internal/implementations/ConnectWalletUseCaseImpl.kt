package io.newm.shared.internal.implementations

import io.newm.shared.internal.repositories.ConnectWalletManager
import io.newm.shared.public.usecases.ConnectWalletUseCase

internal class ConnectWalletUseCaseImpl(
    private val repository: ConnectWalletManager,
) : ConnectWalletUseCase {
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
