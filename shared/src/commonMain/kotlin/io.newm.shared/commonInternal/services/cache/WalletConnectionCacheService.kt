package io.newm.shared.commonInternal.services.cache

import io.newm.shared.commonInternal.services.db.NewmDatabaseWrapper
import io.newm.shared.commonInternal.services.db.cacheWalletConnections
import io.newm.shared.commonInternal.services.db.deleteAllWalletConnections
import io.newm.shared.commonInternal.services.db.deleteWalletConnectionById
import io.newm.shared.commonInternal.services.db.findWalletConnectionByID
import io.newm.shared.commonInternal.services.db.getWalletConnections
import io.newm.shared.commonPublic.models.WalletConnection
import kotlinx.coroutines.flow.Flow

class WalletConnectionCacheService(
    private val db: NewmDatabaseWrapper,
) {
    fun getWalletConnections(): Flow<List<WalletConnection>> = db.getWalletConnections()

    fun findWalletConnectionByID(id: String): Flow<WalletConnection?> = db.findWalletConnectionByID(id)

    suspend fun cacheWalletConnections(connections: List<WalletConnection>) = db.cacheWalletConnections(connections)

    suspend fun deleteAllWalletConnections() = db.deleteAllWalletConnections()

    suspend fun deleteWalletConnectionsById(id: String) = db.deleteWalletConnectionById(id)
}
