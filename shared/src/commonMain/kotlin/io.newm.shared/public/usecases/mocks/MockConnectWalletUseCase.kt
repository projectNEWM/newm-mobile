package io.newm.shared.public.usecases.mocks

import io.newm.shared.public.usecases.ConnectWalletUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockConnectWalletUseCase : ConnectWalletUseCase {
    private var xpub: String? = null

    override fun connect(xpub: String) {
        this.xpub = xpub
    }

    override fun disconnect() {
        xpub = null
    }

    override fun isConnected(): Boolean {
        return xpub != null
    }

    override fun isConnectedFlow(): Flow<Boolean> = flow<Boolean> { emit(isConnected()) }
}