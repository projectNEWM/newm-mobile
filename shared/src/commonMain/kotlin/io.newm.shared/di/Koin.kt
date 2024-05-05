package io.newm.shared.di

import io.ktor.client.engine.HttpClientEngine
import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.shared.config.NewmSharedBuildConfigImpl
import io.newm.shared.internal.TokenManager
import io.newm.shared.internal.implementations.ChangePasswordUseCaseImpl
import io.newm.shared.internal.implementations.ConnectWalletUseCaseImpl
import io.newm.shared.internal.implementations.ForceAppUpdateUseCaseImpl
import io.newm.shared.internal.implementations.GetGenresUseCaseImpl
import io.newm.shared.internal.implementations.LoginUseCaseImpl
import io.newm.shared.internal.implementations.ResetPasswordUseCaseImpl
import io.newm.shared.internal.implementations.SignupUseCaseImpl
import io.newm.shared.internal.implementations.UserDetailsUseCaseImpl
import io.newm.shared.internal.implementations.UserSessionUseCaseImpl
import io.newm.shared.internal.implementations.WalletNFTTracksUseCaseImpl
import io.newm.shared.internal.repositories.CardanoWalletRepository
import io.newm.shared.internal.repositories.GenresRepository
import io.newm.shared.internal.repositories.LogInRepository
import io.newm.shared.internal.repositories.RemoteConfigRepositoryImpl
import io.newm.shared.internal.repositories.NewmPolicyIdsRepository
import io.newm.shared.internal.repositories.PlaylistRepository
import io.newm.shared.internal.repositories.RemoteConfigRepository
import io.newm.shared.internal.repositories.UserRepository
import io.newm.shared.internal.services.CardanoWalletAPI
import io.newm.shared.internal.services.GenresAPI
import io.newm.shared.internal.services.LoginAPI
import io.newm.shared.internal.services.NEWMWalletConnectionAPI
import io.newm.shared.internal.services.RemoteConfigAPI
import io.newm.shared.internal.services.NewmPolicyIdsAPI
import io.newm.shared.internal.services.PlaylistAPI
import io.newm.shared.internal.services.UserAPI
import io.newm.shared.public.usecases.ChangePasswordUseCase
import io.newm.shared.public.usecases.ConnectWalletUseCase
import io.newm.shared.public.usecases.ForceAppUpdateUseCase
import io.newm.shared.public.usecases.GetGenresUseCase
import io.newm.shared.public.usecases.LoginUseCase
import io.newm.shared.public.usecases.ResetPasswordUseCase
import io.newm.shared.public.usecases.SignupUseCase
import io.newm.shared.public.usecases.UserDetailsUseCase
import io.newm.shared.public.usecases.UserSessionUseCase
import io.newm.shared.public.usecases.WalletNFTTracksUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
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
    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }
    single { createHttpClient(get(), get(), get(), get(), enableNetworkLogs = enableNetworkLogs, get()) }
    single { createJson() }
    // Internal Configurations
    single { NewmSharedBuildConfigImpl }
    single { NewmSharedBuildConfigImpl }
    single { TokenManager(get()) }
    // Internal API Services
    single { CardanoWalletAPI(get()) }
    single { GenresAPI(get()) }
    single { LoginAPI(get()) }
    single { NewmPolicyIdsAPI(get()) }
    single { NEWMWalletConnectionAPI(get()) }
    single { PlaylistAPI(get()) }
    single { UserAPI(get()) }
    // Internal Repositories
    single { CardanoWalletRepository(get(), get(), get(), get(), get()) }
    single { GenresRepository() }
    single { LogInRepository() }
    single { NewmPolicyIdsRepository(get(), get(), get()) }
    single { PlaylistRepository() }
    single<RemoteConfigRepository> { RemoteConfigRepositoryImpl(get()) }
    single { UserRepository(get(), get()) }
    // External Use Cases to be consumed outside of KMM
    single<ChangePasswordUseCase> { ChangePasswordUseCaseImpl(get()) }
    single<ConnectWalletUseCase> { ConnectWalletUseCaseImpl(get()) }
    single<ForceAppUpdateUseCase> { ForceAppUpdateUseCaseImpl(get(), get()) }
    single<GetGenresUseCase> { GetGenresUseCaseImpl(get()) }
    single<LoginUseCase> { LoginUseCaseImpl(get(), get()) }
    single<ResetPasswordUseCase> { ResetPasswordUseCaseImpl(get()) }
    single<SignupUseCase> { SignupUseCaseImpl(get()) }
    single<UserDetailsUseCase> { UserDetailsUseCaseImpl(get()) }
    single<GetGenresUseCase> { GetGenresUseCaseImpl(get()) }
    single<WalletNFTTracksUseCase> { WalletNFTTracksUseCaseImpl(get()) }
    single<ConnectWalletUseCase> { ConnectWalletUseCaseImpl(get()) }
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
    repository: LogInRepository,
    tokenManager: TokenManager,
    enableNetworkLogs: Boolean,
    buildConfig: NewmSharedBuildConfig,
): NetworkClientFactory =
    NetworkClientFactory(
        httpClientEngine,
        json,
        repository,
        tokenManager,
        enableNetworkLogs,
        buildConfig
    )
