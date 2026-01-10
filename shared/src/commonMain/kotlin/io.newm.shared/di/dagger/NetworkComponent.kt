package io.newm.shared.di.dagger

import io.newm.shared.commonInternal.SessionManager
import io.newm.shared.commonInternal.TokenManager
import io.newm.shared.commonInternal.db.PreferencesDataStore
import io.newm.shared.commonInternal.repositories.LogInRepository
import io.newm.shared.commonInternal.services.db.NewmDatabaseWrapper
import io.newm.shared.di.NetworkClientFactory
import io.newm.shared.di.createJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides

interface NetworkComponent {
    @Provides
    fun provideJson(): Json = createJson()

    @Provides
    fun provideEnableNetworkLogs(): Boolean = true

    @Provides
    @ApplicationScope
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    @Provides
    @ApplicationScope
    fun provideSessionManager(
        tokenManager: TokenManager,
        db: NewmDatabaseWrapper,
        dataStore: PreferencesDataStore
    ): SessionManager = SessionManager(tokenManager, db, dataStore)

    @Provides
    @ApplicationScope
    fun provideAuthHttpClient(factory: NetworkClientFactory): AuthHttpClient = AuthHttpClient(factory.authHttpClient())

    @Provides
    @ApplicationScope
    fun provideBaseHttpClient(factory: NetworkClientFactory): BaseHttpClient = BaseHttpClient(factory.httpClient())
}