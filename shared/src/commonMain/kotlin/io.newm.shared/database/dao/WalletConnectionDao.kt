package io.newm.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import io.newm.shared.database.entries.DBWalletConnection
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for accessing the DBWalletConnection table.
 */
@Dao
interface WalletConnectionDao {

    /**
     * Retrieves all wallet connections from the database.
     *
     * @return A Flow that emits a list of all wallet connections.
     */
    @Query("SELECT * FROM DBWalletConnection")
    fun getAll(): Flow<List<DBWalletConnection>>

    /**
     * Retrieves a wallet connection by its ID.
     *
     * @param id The ID of the wallet connection.
     * @return The wallet connection with the specified ID, or null if it does not exist.
     */
    @Query("Select * from DBWalletConnection where id = :id")
    suspend fun getById(id: String): DBWalletConnection?

    /**
     * Deletes all wallet connections from the database.
     */
    @Query("DELETE FROM DBWalletConnection")
    suspend fun deleteAll()

    /**
     * Deletes a wallet connection by its ID.
     *
     * @param id The ID of the wallet connection to delete.
     */
    @Query("DELETE FROM DBWalletConnection WHERE id = :id")
    suspend fun deleteById(id: String)

    /**
     * Inserts a wallet connection into the database. If the wallet connection already exists, it will be updated.
     *
     * @param walletConnection The wallet connection to insert or update.
     */
    @Upsert
    suspend fun upsert(walletConnection: DBWalletConnection)

    /**
     * Inserts a wallet connection into the database. If the wallet connection already exists, the insert operation will be ignored.
     *
     * @param walletConnection The wallet connection to insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(walletConnection: DBWalletConnection)

    /**
     * Inserts a list of wallet connections into the database. If a wallet connection already exists, the insert operation for that connection will be ignored.
     *
     * @param walletConnections The list of wallet connections to insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(walletConnections: List<DBWalletConnection>)
}