package io.newm.shared.config

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import io.newm.shared.database.NewmAppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

fun getNewmAppDatabase(
    builder: RoomDatabase.Builder<NewmAppDatabase>
): NewmAppDatabase {
    return builder
        .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}