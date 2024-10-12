package io.newm.shared.internal.services.cache

import io.newm.shared.database.ENABLE_ROOM_DATABASE
import io.newm.shared.database.NewmAppDatabase
import io.newm.shared.database.entries.toDBWalletConnection
import io.newm.shared.database.entries.toWalletConnection
import io.newm.shared.internal.services.db.NewmDatabaseWrapper
import io.newm.shared.internal.services.db.cacheWalletConnections
import io.newm.shared.internal.services.db.deleteAllWalletConnections
import io.newm.shared.internal.services.db.deleteWalletConnectionById
import io.newm.shared.internal.services.db.getWalletConnectionById
import io.newm.shared.internal.services.db.getWalletConnections
import io.newm.shared.public.models.WalletConnection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class WalletConnectionCacheService(
    private val db: NewmDatabaseWrapper,
    private val database: NewmAppDatabase
)  {
    fun getWalletConnections(): Flow<List<WalletConnection>> {
        return if(ENABLE_ROOM_DATABASE) {
            database.walletConnectionDao().getAll().map { list ->
                list.map {
                    it.toWalletConnection()
                }
            }
        } else {
            db.getWalletConnections()
        }
    }

    suspend fun getById(id: String): WalletConnection? {
        return if (ENABLE_ROOM_DATABASE) {
            database.walletConnectionDao().getById(id)?.toWalletConnection()
        } else {
            db.getWalletConnectionById(id).firstOrNull()
        }
    }


    suspend fun cacheWalletConnections(connections: List<WalletConnection>) {
        if(ENABLE_ROOM_DATABASE) {
            database.walletConnectionDao().insertAll(connections.map { it.toDBWalletConnection() })
        } else {
            db.cacheWalletConnections(connections)
        }

    }

    suspend fun deleteAllWalletConnections() {
        if (ENABLE_ROOM_DATABASE) {
            database.walletConnectionDao().deleteAll()
        } else {
            db.deleteAllWalletConnections()
        }
    }

    suspend fun deleteWalletConnectionsById(id: String) {
        if (ENABLE_ROOM_DATABASE) {
            database.walletConnectionDao().deleteById(id)
        } else {
            db.deleteWalletConnectionById(id)
        }
    }
}
