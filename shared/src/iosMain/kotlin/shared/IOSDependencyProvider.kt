package shared

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonInternal.TokenManager
import io.newm.shared.commonInternal.db.PreferencesDataStore
import io.newm.shared.commonInternal.services.db.NewmDatabaseWrapper
import io.newm.shared.db.cache.NewmDatabase
import io.newm.shared.internal.implementations.PreferencesDataStoreImpl
import io.newm.shared.internal.implementations.TokenManagerImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import me.tatarka.inject.annotations.Provides


actual interface OSDependencyProvider {

    actual val preferencesDataStore: PreferencesDataStore
    actual val tokenManager: TokenManager
    actual val db: NewmDatabaseWrapper
    actual val coroutineScope: CoroutineScope

    @Provides
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    @Provides
    fun providePreferencesDataStore(): PreferencesDataStore = PreferencesDataStoreImpl()

    @Provides
    fun providesNewmDatabaseWrapper(): NewmDatabaseWrapper {
        val driver = NativeSqliteDriver(NewmDatabase.Schema, "newm.db")
        return NewmDatabaseWrapper(NewmDatabase(driver))
    }

    @Provides
    fun providesHttpClientEngine(): HttpClientEngine {
        return Darwin.create()
    }

    @Provides
    fun providesTokenManager(storage: PreferencesDataStore, logger: NewmAppLogger): TokenManager {
        return TokenManagerImpl(storage, logger)
    }

    @Provides
    fun providePlatformName(): String = "IOS"
}