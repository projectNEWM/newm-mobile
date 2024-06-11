package shared

import com.squareup.sqldelight.android.AndroidSqliteDriver
import io.ktor.client.engine.android.Android
import io.newm.shared.internal.services.db.NewmDatabaseWrapper
import io.newm.shared.db.cache.NewmDatabase
import io.newm.shared.internal.db.PreferencesDataStore
import io.newm.shared.internal.implementations.PreferencesDataStoreImpl
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = AndroidSqliteDriver(NewmDatabase.Schema, get(), "newm.db")
        NewmDatabaseWrapper(NewmDatabase(driver))
    }
    single { Android.create() }
    single<PreferencesDataStore> { PreferencesDataStoreImpl(get()) }
}

actual fun getPlatformName(): String = "Android"