package io.newm.shared.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.newm.shared.database.entries.DBPolicyId
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for accessing the DBPolicyId table.
 */
@Dao
interface NewmPolicyIdDao {
    /**
     * Retrieves all policy IDs from the database.
     *
     * @return A Flow that emits a list of all policy IDs.
     */
    @Query("SELECT * FROM DBPolicyId")
    fun getAll(): Flow<List<DBPolicyId>>

    /**
     * Retrieves a policy ID by its value.
     *
     * @param id The value of the policy ID.
     * @return The policy ID with the specified value, or null if it does not exist.
     */
    @Query("SELECT * FROM DBPolicyId WHERE id = :id")
    suspend fun getById(id: String): DBPolicyId?

    /**
     * Deletes a specific policy ID from the database.
     *
     * @param policyId The policy ID to delete.
     */
    @Delete
    suspend fun delete(policyId: DBPolicyId)

    /**
     * Deletes all policy IDs from the database.
     */
    @Query("DELETE FROM DBPolicyId")
    suspend fun deleteAll()

    /**
     * Inserts a policy ID into the database. If the policy ID already exists, the insert operation will be ignored.
     *
     * @param policyId The policy ID to insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(policyId: DBPolicyId)

    /**
     * Inserts a list of policy IDs into the database. If a policy ID already exists, the insert operation for that ID will be ignored.
     *
     * @param policyIds The list of policy IDs to insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(policyIds: List<DBPolicyId>)
}