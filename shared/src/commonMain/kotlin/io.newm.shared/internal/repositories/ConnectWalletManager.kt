package io.newm.shared.internal.repositories

import com.liftric.kvault.KVault
import org.koin.core.component.KoinComponent

internal class ConnectWalletManager(
    private val storage: KVault
) : KoinComponent {
    fun connect(xpub: String) {
        storage.set(WALLET_CONNECT_XPUB_KEY, xpub)
    }

    fun disconnect() {
        storage.deleteObject(WALLET_CONNECT_XPUB_KEY)
    }

    fun isConnected(): Boolean {
        return storage.string(WALLET_CONNECT_XPUB_KEY) != null
    }

    fun getXpub(): String? {
        return storage.string(WALLET_CONNECT_XPUB_KEY)
    }

    companion object {
        private const val WALLET_CONNECT_XPUB_KEY = "WALLET_CONNECT_XPUB_KEY"
    }
}