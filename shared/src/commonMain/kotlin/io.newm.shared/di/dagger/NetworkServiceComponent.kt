package io.newm.shared.di.dagger

import io.newm.shared.NewmAppLogger
import io.newm.shared.di.NetworkClientFactory
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
import me.tatarka.inject.annotations.Provides

interface NetworkServiceComponent {

    @Provides
    fun provideCardanoWalletAPI(networkClient: NetworkClientFactory): CardanoWalletAPI =
        CardanoWalletAPI(networkClient)

    @Provides
    fun providesNFTNetworkService(api: CardanoWalletAPI): NFTNetworkService = NFTNetworkService(api)

    @Provides
    fun provideEarningsAPI(
        networkClient: NetworkClientFactory,
        logger: NewmAppLogger
    ): EarningsAPI = EarningsAPI(networkClient, logger)

    @Provides
    fun provideEarningsNetworkService(
        api: EarningsAPI
    ): EarningsNetworkService = EarningsNetworkService(api)

    @Provides
    fun providesFeatureFlagService(
        dataSource: FeatureFlagDataSource,
        preferencesStore: PreferencesDataStore,
        logger: NewmAppLogger
    ): FeatureFlagService = DefaultFeatureFlagService(dataSource, preferencesStore, logger)

    @Provides
    fun provideLoginAPI(
        networkClient: NetworkClientFactory,
        logger: NewmAppLogger
    ): LoginAPI = LoginAPI(networkClient, logger)

    @Provides
    fun providesNEWMWalletConnectionAPI(
        networkClient: NetworkClientFactory,
    ): NEWMWalletConnectionAPI = NEWMWalletConnectionAPI(networkClient)

    @Provides
    fun providesPlaylistAPI(networkClient: NetworkClientFactory): PlaylistAPI =
        PlaylistAPI(networkClient)

    @Provides
    fun providesRemoteConfigAPI(networkClient: NetworkClientFactory): RemoteConfigAPI =
        RemoteConfigAPI(networkClient)

    @Provides
    fun providesUserAPI(networkClient: NetworkClientFactory, logger: NewmAppLogger): UserAPI {
        return UserAPI(networkClient, logger)
    }

    @Provides
    fun providesNewmCloudinaryAPI(networkClient: NetworkClientFactory): NewmCloudinaryAPI {
        return NewmCloudinaryAPI(networkClient)
    }
}