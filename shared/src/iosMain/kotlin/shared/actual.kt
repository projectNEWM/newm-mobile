package shared

import com.liftric.kvault.KVault
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import io.ktor.client.engine.darwin.Darwin
import io.newm.shared.internal.db.NewmDatabaseWrapper
import io.newm.shared.db.cache.NewmDatabase
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = NativeSqliteDriver(NewmDatabase.Schema, "newm.db")
        NewmDatabaseWrapper(NewmDatabase(driver))
    }
    single { Darwin.create() }
    single { KVault() }
}