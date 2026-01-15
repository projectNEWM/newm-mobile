package shared

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonInternal.TokenManager
import io.newm.shared.commonInternal.db.PreferencesDataStore
import io.newm.shared.commonInternal.services.db.NewmDatabaseWrapper
import io.newm.shared.db.cache.NewmDatabase
import io.newm.shared.internal.implementations.PreferencesDataStoreImpl
import io.newm.shared.internal.implementations.TokenManagerImpl
import me.tatarka.inject.annotations.Provides

actual interface OSDependencyProvider {

    actual val preferencesDataStore: PreferencesDataStore
    actual val tokenManager: TokenManager
    actual val db: NewmDatabaseWrapper

    @Provides
    fun providePreferencesDataStore(): PreferencesDataStore = PreferencesDataStoreImpl()

    @Provides
    fun providesNewmDatabaseWrapper(): NewmDatabaseWrapper {
        // SQLDelight doesn't support wasmJs drivers yet - null database will throw
        // KMMException if any code tries to access it
        return NewmDatabaseWrapper(null)
    }

    @Provides
    fun providesHttpClientEngine(): HttpClientEngine {
        return Js.create()
    }

    @Provides
    fun providesTokenManager(storage: PreferencesDataStore, logger: NewmAppLogger): TokenManager {
        return TokenManagerImpl(storage, logger)
    }

    @Provides
    fun providePlatformName(): String = "WasmJS"
}