package io.newm.shared.database.entries

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.newm.shared.public.models.WalletConnection
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Entity
data class DBWalletConnection(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val createdAt: String,
    val stakeAddress: String,
    val name: String,
)

fun DBWalletConnection.toWalletConnection(): WalletConnection {
    return WalletConnection(
        id = id,
        createdAt = createdAt,
        stakeAddress = stakeAddress,
        name = name,
    )
}

fun WalletConnection.toDBWalletConnection(): DBWalletConnection {
    return DBWalletConnection(
        id = id,
        createdAt = createdAt,
        stakeAddress = stakeAddress,
        name = name,
    )
}