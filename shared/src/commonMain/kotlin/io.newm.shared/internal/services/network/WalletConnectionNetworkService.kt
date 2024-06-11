package io.newm.shared.internal.services.network

import io.newm.shared.internal.api.NEWMWalletConnectionAPI
import io.newm.shared.public.models.WalletConnection
import org.koin.core.component.KoinComponent

internal class WalletConnectionNetworkService(
    private val walletConnectionAPI: NEWMWalletConnectionAPI
)  {
    suspend fun connectWallet(connectionId: String): WalletConnection =
        walletConnectionAPI.connectWallet(connectionId)

    suspend fun getWalletConnections(): List<WalletConnection> =
        walletConnectionAPI.getWalletConnections()

    suspend fun disconnectWallet(connectionId: String): Boolean {
        val response = walletConnectionAPI.disconnectWallet(connectionId)
        return response.call.response.status.value == 200
    }
}