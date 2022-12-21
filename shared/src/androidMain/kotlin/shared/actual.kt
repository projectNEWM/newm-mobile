package shared

import com.squareup.sqldelight.android.AndroidSqliteDriver
import io.newm.shared.db.cache.NewmDatabase
import io.newm.shared.db.NewmDatabaseWrapper
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = AndroidSqliteDriver(NewmDatabase.Schema, get(), "newm.db")
        NewmDatabaseWrapper(NewmDatabase(driver))
    }
}