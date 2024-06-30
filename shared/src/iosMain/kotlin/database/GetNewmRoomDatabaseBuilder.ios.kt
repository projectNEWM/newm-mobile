package database

import androidx.room.Room
import androidx.room.RoomDatabase
import io.newm.shared.database.NewmAppDatabase
import platform.Foundation.NSHomeDirectory


fun getNewmAppDatabase(): RoomDatabase.Builder<NewmAppDatabase> {
    val dbFile = NSHomeDirectory() + "/newm-app.db"
    return Room.databaseBuilder<NewmAppDatabase>(
        name = dbFile,
        factory =  { NewmAppDatabase::class.instantiateImpl() }
    )
}
