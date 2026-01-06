package shared

import android.accounts.AccountManager
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.ktor.client.engine.android.Android
import io.newm.shared.commonInternal.services.db.NewmDatabaseWrapper
import io.newm.shared.db.cache.NewmDatabase
import io.newm.shared.commonInternal.CloudinaryManager
import io.newm.shared.commonInternal.TokenManager
import io.newm.shared.commonInternal.db.PreferencesDataStore
import io.newm.shared.internal.implementations.CloudinaryManagerImpl
import io.newm.shared.internal.implementations.PreferencesDataStoreImpl
import io.newm.shared.internal.implementations.TokenManagerImpl
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = AndroidSqliteDriver(NewmDatabase.Schema, get(), "newm.db")
        NewmDatabaseWrapper(NewmDatabase(driver))
    }
    single { Android.create() }
    single<PreferencesDataStore> { PreferencesDataStoreImpl(get()) }
    single { AccountManager.get(get()) }
    single<TokenManager> { TokenManagerImpl(get(), get()) }
    single<CloudinaryManager> { CloudinaryManagerImpl(get(), get(), get()) }
}

actual fun getPlatformName(): String = "Android"