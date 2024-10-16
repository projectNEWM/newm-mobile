package io.newm.shared.internal.services.cache

import io.newm.shared.internal.services.db.NewmDatabaseWrapper
import io.newm.shared.internal.services.db.cacheWalletConnections
import io.newm.shared.internal.services.db.deleteAllWalletConnections
import io.newm.shared.internal.services.db.deleteWalletConnectionById
import io.newm.shared.internal.services.db.getWalletConnections
import io.newm.shared.public.models.WalletConnection
import kotlinx.coroutines.flow.Flow

class WalletConnectionCacheService(
    private val db: NewmDatabaseWrapper
)  {
    fun getWalletConnections(): Flow<List<WalletConnection>> =
        db.getWalletConnections()

    suspend fun cacheWalletConnections(connections: List<WalletConnection>) =
        db.cacheWalletConnections(connections)

    suspend fun deleteAllWalletConnections() =
        db.deleteAllWalletConnections()

    suspend fun deleteWalletConnectionsById(id: String) =
        db.deleteWalletConnectionById(id)
}
