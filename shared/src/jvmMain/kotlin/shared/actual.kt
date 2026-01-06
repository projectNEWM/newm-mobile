package shared

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.ktor.client.engine.cio.CIO
import io.newm.shared.db.cache.NewmDatabase
import io.newm.shared.commonInternal.TokenManager
import io.newm.shared.commonInternal.services.db.NewmDatabaseWrapper
import io.newm.shared.commonInternal.db.PreferencesDataStore
import io.newm.shared.internal.implementations.PreferencesDataStoreImpl
import io.newm.shared.internal.implementations.TokenManagerImpl
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        NewmDatabase.Schema.create(driver)
        NewmDatabaseWrapper(NewmDatabase(driver))
    }
    single { CIO.create() }
    single<PreferencesDataStore> { PreferencesDataStoreImpl() }
    single<TokenManager> { TokenManagerImpl(get(), get()) }
}

actual fun getPlatformName(): String = "JVM"
