package database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import io.newm.shared.database.NewmAppDatabase

fun getNewmRoomDatabaseBuilder(context: Context): RoomDatabase.Builder<NewmAppDatabase> {
    val dbFile = context.getDatabasePath("newm-app.db")
    return Room.databaseBuilder<NewmAppDatabase>(
        context = context,
        name = dbFile.absolutePath
    )
}