package io.newm.shared.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.newm.shared.database.entries.DBNFTTrack
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for accessing the DBNFTTrack table.
 */
@Dao
interface NFTTracksDao {

    /**
     * Retrieves all NFT tracks from the database.
     *
     * @return A Flow that emits a list of all NFT tracks.
     */
    @Query("SELECT * FROM DBNFTTrack")
    fun getAll(): Flow<List<DBNFTTrack>>

    /**
     * Retrieves an NFT track by its ID.
     *
     * @param id The ID of the NFT track.
     * @return The NFT track with the specified ID, or null if it does not exist.
     */
    @Query("Select * from DBNFTTrack where id = :id")
    suspend fun getById(id: String): DBNFTTrack?

    /**
     * Retrieves a list of NFT tracks by their ID.
     *
     * @param id The ID of the NFT track.
     * @return A list of NFT tracks with the specified ID, or an empty list if none exist.
     */
    @Query("Select * from DBNFTTrack where id = :id")
    suspend fun getByIdFlow(id: String): List<DBNFTTrack>

    /**
     * Deletes a specific NFT track from the database.
     *
     * @param nftTrack The NFT track to delete.
     */
    @Delete
    suspend fun delete(nftTrack: DBNFTTrack)

    /**
     * Deletes all NFT tracks from the database.
     */
    @Query("DELETE FROM DBNFTTrack")
    suspend fun deleteAll()

    /**
     * Inserts an NFT track into the database. If the NFT track already exists, the insert operation will be ignored.
     *
     * @param nftTrack The NFT track to insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(nftTrack: DBNFTTrack)

    /**
     * Inserts a list of NFT tracks into the database. If an NFT track already exists, the insert operation for that track will be ignored.
     *
     * @param nftTracks The list of NFT tracks to insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(nftTracks: List<DBNFTTrack>)
}