package io.newm.shared.di.dagger

import io.newm.shared.NewmAppLogger
import io.newm.shared.commonInternal.EarningsAPI
import io.newm.shared.commonInternal.api.CardanoWalletAPI
import io.newm.shared.commonInternal.api.LoginAPI
import io.newm.shared.commonInternal.api.NEWMWalletConnectionAPI
import io.newm.shared.commonInternal.api.NewmCloudinaryAPI
import io.newm.shared.commonInternal.api.PlaylistAPI
import io.newm.shared.commonInternal.api.RemoteConfigAPI
import io.newm.shared.commonInternal.api.UserAPI
import io.newm.shared.commonInternal.db.PreferencesDataStore
import io.newm.shared.commonInternal.services.network.EarningsNetworkService
import io.newm.shared.commonInternal.services.network.NFTNetworkService
import io.newm.shared.commonPublic.featureflags.DefaultFeatureFlagService
import io.newm.shared.commonPublic.featureflags.FeatureFlagDataSource
import io.newm.shared.commonPublic.featureflags.FeatureFlagService
import io.newm.shared.config.NewmSharedBuildConfig
import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Provides

interface NetworkServiceComponent {

    @Provides
    fun provideCardanoWalletAPI(authHttpClient: AuthHttpClient): CardanoWalletAPI =
        CardanoWalletAPI(authHttpClient.client)

    @Provides
    fun providesNFTNetworkService(api: CardanoWalletAPI): NFTNetworkService = NFTNetworkService(api)

    @Provides
    fun provideEarningsAPI(
        authHttpClient: AuthHttpClient,
        logger: NewmAppLogger
    ): EarningsAPI = EarningsAPI(authHttpClient.client, logger)

    @Provides
    fun provideEarningsNetworkService(
        api: EarningsAPI
    ): EarningsNetworkService = EarningsNetworkService(api)

    @Provides
    fun providesFeatureFlagService(
        dataSource: FeatureFlagDataSource,
        preferencesStore: PreferencesDataStore,
        buildConfig: NewmSharedBuildConfig,
        logger: NewmAppLogger,
        scope: CoroutineScope
    ): FeatureFlagService = DefaultFeatureFlagService(dataSource, preferencesStore, buildConfig, logger, scope)

    @Provides
    fun provideLoginAPI(
        baseHttpClient: BaseHttpClient,
        logger: NewmAppLogger
    ): LoginAPI = LoginAPI(baseHttpClient.client, logger)

    @Provides
    fun providesNEWMWalletConnectionAPI(
        authHttpClient: AuthHttpClient,
    ): NEWMWalletConnectionAPI = NEWMWalletConnectionAPI(authHttpClient.client)

    @Provides
    fun providesPlaylistAPI(authHttpClient: AuthHttpClient): PlaylistAPI =
        PlaylistAPI(authHttpClient.client)

    @Provides
    fun providesRemoteConfigAPI(baseHttpClient: BaseHttpClient): RemoteConfigAPI =
        RemoteConfigAPI(baseHttpClient.client)

    @Provides
    fun providesUserAPI(authHttpClient: AuthHttpClient, logger: NewmAppLogger): UserAPI {
        return UserAPI(authHttpClient.client, logger)
    }

    @Provides
    fun providesNewmCloudinaryAPI(authHttpClient: AuthHttpClient): NewmCloudinaryAPI {
        return NewmCloudinaryAPI(authHttpClient.client)
    }
}