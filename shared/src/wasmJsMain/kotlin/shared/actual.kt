package shared

import io.ktor.client.engine.js.Js
import io.newm.shared.commonInternal.TokenManager
import io.newm.shared.commonInternal.db.PreferencesDataStore
import io.newm.shared.commonInternal.services.db.NewmDatabaseWrapper
import io.newm.shared.internal.implementations.PreferencesDataStoreImpl
import io.newm.shared.internal.implementations.TokenManagerImpl
import org.koin.dsl.module

actual fun platformModule() = module {
    // Note: SQLDelight doesn't have a wasmJs driver yet, so database functionality is stubbed
    single<NewmDatabaseWrapper> { NewmDatabaseWrapper(null) }
    single { Js.create() }
    single<PreferencesDataStore> { PreferencesDataStoreImpl() }
    single<TokenManager> { TokenManagerImpl(get(), get()) }
}

actual fun getPlatformName(): String = "WasmJS"