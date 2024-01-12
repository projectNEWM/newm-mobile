package io.newm.shared.internal.repositories

import com.liftric.kvault.KVault
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent

internal class ConnectWalletManager(
    private val storage: KVault
) : KoinComponent {

    private val _connectionState = MutableStateFlow(isConnected())
    val connectionState: StateFlow<Boolean> = _connectionState

    fun connect(xPub: String) {
        storage.set(WALLET_CONNECT_XPUB_KEY, xPub)
        _connectionState.value = true
    }

    fun disconnect() {
        storage.deleteObject(WALLET_CONNECT_XPUB_KEY)
        _connectionState.value = false
    }

    fun isConnected(): Boolean {
        return storage.string(WALLET_CONNECT_XPUB_KEY) != null
    }


    fun getXPub(): String? {
        return storage.string(WALLET_CONNECT_XPUB_KEY)
    }

    companion object {
        private const val WALLET_CONNECT_XPUB_KEY = "WALLET_CONNECT_XPUB_KEY"
    }
}