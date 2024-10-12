package io.newm.shared.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.newm.shared.database.dao.NFTTracksDao
import io.newm.shared.database.dao.WalletConnectionDao
import io.newm.shared.database.entries.DBNFTTrack
import io.newm.shared.database.entries.DBWalletConnection
import io.newm.shared.database.sqlite.DataConverters

const val ENABLE_ROOM_DATABASE = true

@Database(
    entities = [
        DBNFTTrack::class,
        DBWalletConnection::class,
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(value = [DataConverters::class])
abstract class NewmAppDatabase : RoomDatabase() {

    abstract fun nftTracksDao(): NFTTracksDao

    abstract fun walletConnectionDao(): WalletConnectionDao

}
