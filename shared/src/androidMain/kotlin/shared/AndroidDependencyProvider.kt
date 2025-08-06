package shared

import android.accounts.AccountManager
import android.app.Application
import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import io.newm.shared.NewmAppLogger
import io.newm.shared.db.cache.NewmDatabase
import io.newm.shared.commonInternal.CloudinaryManager
import io.newm.shared.commonInternal.TokenManager
import io.newm.shared.commonInternal.api.NewmCloudinaryAPI
import io.newm.shared.internal.implementations.PreferencesDataStoreImpl
import io.newm.shared.commonInternal.db.PreferencesDataStore
import io.newm.shared.commonInternal.services.db.NewmDatabaseWrapper
import io.newm.shared.internal.implementations.CloudinaryManagerImpl
import io.newm.shared.internal.implementations.TokenManagerImpl
import me.tatarka.inject.annotations.Provides

actual interface OSDependencyProvider {

    actual val preferencesDataStore: PreferencesDataStore

    @Provides
    fun providePreferencesDataStore(application: Application): PreferencesDataStore = PreferencesDataStoreImpl(application)

    @Provides
    fun providesNewmDatabaseWrapper(context: Context): NewmDatabaseWrapper {
        val driver = AndroidSqliteDriver(NewmDatabase.Schema, context, "newm.db")
        return NewmDatabaseWrapper(NewmDatabase(driver))
    }

    @Provides
    fun providesHttpClientEngine(): HttpClientEngine {
        return Android.create()
    }

    @Provides
    fun providesAccountManager(context: Context): AccountManager {
        return AccountManager.get(context)
    }

    @Provides
    fun providesTokenManager(context: Context, accountManager: AccountManager): TokenManager {
        return TokenManagerImpl(context, accountManager)
    }

    @Provides
    fun providesCloudinaryManager(context: Context, newmCloudinaryAPI: NewmCloudinaryAPI, logger: NewmAppLogger): CloudinaryManager {
        return CloudinaryManagerImpl(context, newmCloudinaryAPI, logger)
    }

    @Provides
    fun providePlatformName(): String = "Android"
}