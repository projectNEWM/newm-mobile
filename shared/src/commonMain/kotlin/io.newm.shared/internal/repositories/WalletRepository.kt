package io.newm.shared.internal.repositories

import io.newm.shared.NewmAppLogger
import io.newm.shared.internal.services.cache.WalletConnectionCacheService
import io.newm.shared.internal.services.network.WalletConnectionNetworkService
import io.newm.shared.public.models.WalletConnection
import io.newm.shared.public.models.error.KMMException
import kotlinx.coroutines.flow.Flow

internal class WalletRepository(
    private val networkService: WalletConnectionNetworkService,
    private val cacheService: WalletConnectionCacheService,
    private val logger: NewmAppLogger
) {
    fun getWalletConnectionsCache(): Flow<List<WalletConnection>> =
        cacheService.getWalletConnections()

    suspend fun syncWalletConnectionsFromNetworkToDB(): List<WalletConnection> {
        return try {
            val connections = networkService.getWalletConnections()
            cacheService.deleteAllWalletConnections()
            cacheService.cacheWalletConnections(connections)
            connections
        } catch (e: Exception) {
            logger.error("WalletRepository", "Error fetching wallet connections from network ${e.cause}", e)
            return emptyList()
        }
    }

    suspend fun connectWallet(newmCode: String): WalletConnection? {
        return try {
            val newConnection = networkService.connectWallet(newmCode.removePrefix("newm-"))
            cacheService.cacheWalletConnections(listOf(newConnection))
            newConnection
        } catch (e: Exception) {
            logger.error("WalletRepository", "Error connecting wallet ${e.cause}", e)
            null
        }
    }

    suspend fun disconnectWallet(walletConnectionId: String): Boolean {
        return try {
            val success = networkService.disconnectWallet(walletConnectionId)
            if (success) {
                cacheService.deleteWalletConnectionsById(walletConnectionId)
            } else {
                throw KMMException("Error disconnecting wallet")
            }
            success
        } catch (e: Exception) {
            logger.error("WalletRepository", "Error disconnecting wallet ${e.cause}", e)
            false
        }
    }
}
