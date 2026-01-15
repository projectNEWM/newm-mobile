package io.newm.shared.di

import io.ktor.client.engine.HttpClientEngine
import io.newm.shared.NewmAppLogger
import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.shared.config.NewmSharedBuildConfigImpl
import io.newm.shared.commonInternal.EarningsAPI
import io.newm.shared.commonInternal.SessionManager
import io.newm.shared.commonInternal.TokenManager
import io.newm.shared.commonInternal.api.CardanoWalletAPI
import io.newm.shared.commonInternal.api.GenresAPI
import io.newm.shared.commonInternal.api.LoginAPI
import io.newm.shared.commonInternal.api.NEWMWalletConnectionAPI
import io.newm.shared.commonInternal.api.NewmCloudinaryAPI
import io.newm.shared.commonInternal.api.PlaylistAPI
import io.newm.shared.commonInternal.api.RemoteConfigAPI
import io.newm.shared.commonInternal.api.UserAPI
import io.newm.shared.commonInternal.implementations.ChangePasswordUseCaseImpl
import io.newm.shared.commonInternal.implementations.ConnectWalletUseCaseImpl
import io.newm.shared.commonInternal.implementations.DeleteCurrentUserUseCaseImpl
import io.newm.shared.commonInternal.implementations.DisconnectWalletUseCaseImpl
import io.newm.shared.commonInternal.implementations.FindWalletConnectionUseCaseImpl
import io.newm.shared.commonInternal.implementations.ForceAppUpdateUseCaseImpl
import io.newm.shared.commonInternal.implementations.GetGenresUseCaseImpl
import io.newm.shared.commonInternal.implementations.GetInvestmentPortfolioDataUseCaseImpl
import io.newm.shared.commonInternal.implementations.GetWalletConnectionsUseCaseImpl
import io.newm.shared.commonInternal.implementations.HasWalletConnectionsUseCaseImpl
import io.newm.shared.commonInternal.implementations.LoginUseCaseImpl
import io.newm.shared.commonInternal.implementations.ResetPasswordUseCaseImpl
import io.newm.shared.commonInternal.implementations.SignupUseCaseImpl
import io.newm.shared.commonInternal.implementations.SyncWalletConnectionsUseCaseImpl
import io.newm.shared.commonInternal.implementations.UpdateProfilePictureUseCaseImpl
import io.newm.shared.commonInternal.implementations.UserDetailsUseCaseImpl
import io.newm.shared.commonInternal.implementations.UserSessionUseCaseImpl
import io.newm.shared.commonInternal.implementations.WalletNFTTracksUseCaseImpl
import io.newm.shared.commonInternal.repositories.EarningsRepository
import io.newm.shared.commonInternal.repositories.GenresRepository
import io.newm.shared.commonInternal.repositories.LogInRepository
import io.newm.shared.commonInternal.repositories.NFTRepository
import io.newm.shared.commonInternal.repositories.PlaylistRepository
import io.newm.shared.commonInternal.repositories.RemoteConfigRepository
import io.newm.shared.commonInternal.repositories.RemoteConfigRepositoryImpl
import io.newm.shared.commonInternal.repositories.UserRepository
import io.newm.shared.commonInternal.repositories.WalletRepository
import io.newm.shared.commonInternal.services.cache.NFTCacheService
import io.newm.shared.commonInternal.services.cache.WalletConnectionCacheService
import io.newm.shared.commonInternal.services.network.EarningsNetworkService
import io.newm.shared.commonInternal.services.network.NFTNetworkService
import io.newm.shared.commonInternal.services.network.WalletConnectionNetworkService
import io.newm.shared.commonInternal.store.NftTrackStore
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.featureflags.DefaultFeatureFlagService
import io.newm.shared.commonPublic.featureflags.FeatureFlagService
import io.newm.shared.commonPublic.usecases.ChangePasswordUseCase
import io.newm.shared.commonPublic.usecases.ConnectWalletUseCase
import io.newm.shared.commonPublic.usecases.DeleteCurrentUserUseCase
import io.newm.shared.commonPublic.usecases.DisconnectWalletUseCase
import io.newm.shared.commonPublic.usecases.FindWalletConnectionUseCase
import io.newm.shared.commonPublic.usecases.ForceAppUpdateUseCase
import io.newm.shared.commonPublic.usecases.GetGenresUseCase
import io.newm.shared.commonPublic.usecases.GetInvestmentPortfolioDataUseCase
import io.newm.shared.commonPublic.usecases.GetWalletConnectionsUseCase
import io.newm.shared.commonPublic.usecases.HasWalletConnectionsUseCase
import io.newm.shared.commonPublic.usecases.LoginUseCase
import io.newm.shared.commonPublic.usecases.ResetPasswordUseCase
import io.newm.shared.commonPublic.usecases.SignupUseCase
import io.newm.shared.commonPublic.usecases.SyncWalletConnectionsUseCase
import io.newm.shared.commonPublic.usecases.UpdateProfilePictureUseCase
import io.newm.shared.commonPublic.usecases.UserDetailsUseCase
import io.newm.shared.commonPublic.usecases.UserSessionUseCase
import io.newm.shared.commonPublic.usecases.WalletNFTTracksUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import shared.platformModule

fun initKoin(enableNetworkLogs: Boolean = true, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(enableNetworkLogs = enableNetworkLogs), platformModule())
    }

// called by iOS etc
//https://johnoreilly.dev/posts/kotlinmultiplatform-koin/
fun initKoin(enableNetworkLogs: Boolean) = initKoin(enableNetworkLogs = enableNetworkLogs) {}

fun commonModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single {
        createHttpClient(
            httpClientEngine = get(),
            json = get(),
            sessionManager = get(),
            tokenManager = get(),
            buildConfig = get(),
            enableNetworkLogs = enableNetworkLogs,
            appLogger = get()
        )
    }
    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }
    single(named("mainScope")) { CoroutineScope(Dispatchers.Main + SupervisorJob()) }
    // Internal Configurations
    single<NewmSharedBuildConfig> { NewmSharedBuildConfigImpl(get(), get(named("mainScope"))) }
    single { NewmAppLogger() }
    single { NewmAppEventLogger() }
    single { SessionManager(get(), get(), get()) }
    single<FeatureFlagService> { DefaultFeatureFlagService(get(), get(), get(), get(), get()) }
    // Internal API Services
    single { CardanoWalletAPI(get(named("auth"))) }
    single { EarningsAPI(get(named("auth")), get()) }
    single { GenresAPI(get()) }
    single { LoginAPI(get(named("public")), get()) }
    single { NEWMWalletConnectionAPI(get(named("auth"))) }
    single { PlaylistAPI(get(named("auth"))) }
    single { RemoteConfigAPI(get(named("public"))) }
    single { UserAPI(get(named("auth")), get()) }
    single { NewmCloudinaryAPI(get(named("auth"))) }
    // Internal Services
    single { EarningsNetworkService(get()) }
    single { NFTCacheService(get()) }
    single { NFTNetworkService(get()) }
    single { NftTrackStore(get(), get()) }
    single { WalletConnectionCacheService(get()) }
    single { WalletConnectionNetworkService(get()) }
    // Internal Repositories
    single { EarningsRepository(get(), get()) }
    single { GenresRepository() }
    single { LogInRepository(get(), get(), get(), get()) }
    single { NFTRepository(get()) }
    single { PlaylistRepository() }
    single { UserRepository(get(), get(), get()) }
    single { WalletRepository(get(), get(), get()) }
    single<RemoteConfigRepository> { RemoteConfigRepositoryImpl(get(), get()) }
    // External Use Cases to be consumed outside of KMM
    single<ChangePasswordUseCase> { ChangePasswordUseCaseImpl(get()) }
    single<ConnectWalletUseCase> { ConnectWalletUseCaseImpl(get(), get()) }
    single<DeleteCurrentUserUseCase> { DeleteCurrentUserUseCaseImpl(get(), get()) }
    single<DisconnectWalletUseCase> { DisconnectWalletUseCaseImpl(get(), get()) }
    single<FindWalletConnectionUseCase> { FindWalletConnectionUseCaseImpl(get()) }
    single<ForceAppUpdateUseCase> { ForceAppUpdateUseCaseImpl(get()) }
    single<GetGenresUseCase> { GetGenresUseCaseImpl(get()) }
    single<GetInvestmentPortfolioDataUseCase> { GetInvestmentPortfolioDataUseCaseImpl(get()) }
    single<GetWalletConnectionsUseCase> { GetWalletConnectionsUseCaseImpl(get()) }
    single<HasWalletConnectionsUseCase> { HasWalletConnectionsUseCaseImpl(get()) }
    single<LoginUseCase> { LoginUseCaseImpl( get(), get()) }
    single<ResetPasswordUseCase> { ResetPasswordUseCaseImpl(get()) }
    single<SignupUseCase> { SignupUseCaseImpl(get()) }
    single<SyncWalletConnectionsUseCase> { SyncWalletConnectionsUseCaseImpl(get()) }
    single<UpdateProfilePictureUseCase> { UpdateProfilePictureUseCaseImpl(get(), get()) }
    single<UserDetailsUseCase> { UserDetailsUseCaseImpl(get()) }
    single<UserSessionUseCase> { UserSessionUseCaseImpl(get()) }
    single<WalletNFTTracksUseCase> { WalletNFTTracksUseCaseImpl(get()) }
}

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    encodeDefaults = true
}

internal fun createHttpClient(
    httpClientEngine: HttpClientEngine,
    json: Json,
    tokenManager: TokenManager,
    sessionManager: SessionManager,
    enableNetworkLogs: Boolean,
    buildConfig: NewmSharedBuildConfig,
    appLogger: NewmAppLogger
): NetworkClientFactory =
    NetworkClientFactory(
        httpClientEngine,
        json,
        tokenManager,
        sessionManager,
        enableNetworkLogs,
        buildConfig,
        appLogger
    )