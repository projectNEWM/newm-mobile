package io.newm.shared.commonInternal.services.network

import io.newm.shared.commonInternal.api.NEWMWalletConnectionAPI
import io.newm.shared.commonPublic.models.WalletConnection

internal class WalletConnectionNetworkService(
    private val walletConnectionAPI: NEWMWalletConnectionAPI
)  {
    suspend fun connectWallet(connectionId: String): WalletConnection =
        walletConnectionAPI.connectWallet(connectionId)

    suspend fun getWalletConnections(): List<WalletConnection> =
        walletConnectionAPI.getWalletConnections()

    suspend fun disconnectWallet(connectionId: String): Boolean {
        val response = walletConnectionAPI.disconnectWallet(connectionId)
        return response.call.response.status.value == 204
    }
}