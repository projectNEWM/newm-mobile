package shared

import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import io.ktor.client.engine.darwin.*
import io.newm.shared.db.cache.NewmDatabase
import io.newm.shared.db.NewmDatabaseWrapper
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = NativeSqliteDriver(NewmDatabase.Schema, "newm.db")
        NewmDatabaseWrapper(NewmDatabase(driver))
    }
    single { Darwin.create() }
}