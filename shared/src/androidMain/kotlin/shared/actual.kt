package shared

import com.liftric.kvault.KVault
import com.squareup.sqldelight.android.AndroidSqliteDriver
import io.ktor.client.engine.android.Android
import io.newm.shared.db.NewmDatabaseWrapper
import io.newm.shared.db.cache.NewmDatabase
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = AndroidSqliteDriver(NewmDatabase.Schema, get(), "newm.db")
        NewmDatabaseWrapper(NewmDatabase(driver))
    }
    single { Android.create() }
    single { KVault(get(), "user-account") }
}