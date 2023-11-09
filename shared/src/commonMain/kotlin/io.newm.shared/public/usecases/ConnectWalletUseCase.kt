package io.newm.shared.public.usecases

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface ConnectWalletUseCase {
    fun connect(xpub: String)
    fun disconnect()
    fun isConnected(): Boolean
}

class ConnectWalletUseCaseProvider(): KoinComponent {
    private val connectWalletUseCase: ConnectWalletUseCase by inject()

    fun get(): ConnectWalletUseCase {
        return this.connectWalletUseCase
    }
}