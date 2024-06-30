package io.newm.shared.database.entries

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class representing a Policy ID.
 *
 * @param id The policy ID.
 */
@Entity
data class DBPolicyId(
    @PrimaryKey val id: String
)