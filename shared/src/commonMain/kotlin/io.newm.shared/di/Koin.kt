package io.newm.shared.di

import io.ktor.client.engine.HttpClientEngine
import io.newm.shared.TokenManager
import io.newm.shared.login.UserSession
import io.newm.shared.login.UserSessionImpl
import io.newm.shared.login.repository.LogInRepository
import io.newm.shared.login.service.LoginAPI
import io.newm.shared.repositories.CardanoWalletRepository
import io.newm.shared.repositories.GenresRepository
import io.newm.shared.repositories.PlaylistRepository
import io.newm.shared.repositories.UserRepository
import io.newm.shared.repositories.WalletConnectManager
import io.newm.shared.services.GenresAPI
import io.newm.shared.services.PlaylistAPI
import io.newm.shared.services.UserAPI
import io.newm.shared.services.CardanoWalletAPI
import io.newm.shared.usecases.GetGenresUseCase
import io.newm.shared.usecases.GetGenresUseCaseImpl
import io.newm.shared.usecases.LoginUseCase
import io.newm.shared.usecases.LoginUseCaseImpl
import io.newm.shared.usecases.SignupUseCase
import io.newm.shared.usecases.SignupUseCaseImpl
import io.newm.shared.usecases.UserProfileUseCase
import io.newm.shared.usecases.UserProfileUseCaseImpl
import io.newm.shared.usecases.WalletConnectUseCase
import io.newm.shared.usecases.WalletConnectUseCaseImpl
import io.newm.shared.usecases.WalletNFTSongsUseCase
import io.newm.shared.usecases.WalletNFTSongsUseCaseImpl
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
fun initKoin() = initKoin(enableNetworkLogs = true) {}

fun commonModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single { createHttpClient(get(), get(), get(), enableNetworkLogs = enableNetworkLogs) }
    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }
    // Internal API Services
    single { LoginAPI(get()) }
    single { GenresAPI(get()) }
    single { UserAPI(get()) }
    single { PlaylistAPI(get()) }
    single { CardanoWalletAPI(get()) }
    // Internal Repositories
    single { TokenManager() }
    single { LogInRepository() }
    single { GenresRepository() }
    single { UserRepository() }
    single { PlaylistRepository() }
    single { CardanoWalletRepository() }
    single { WalletConnectManager(get()) }
    // External Use Cases to be consumed outside of KMM
    single<LoginUseCase> { LoginUseCaseImpl(get()) }
    single<SignupUseCase> { SignupUseCaseImpl(get()) }
    single<UserProfileUseCase> { UserProfileUseCaseImpl(get()) }
    single<GetGenresUseCase> { GetGenresUseCaseImpl(get()) }
    single<WalletNFTSongsUseCase> { WalletNFTSongsUseCaseImpl(get(), get()) }
    single<WalletConnectUseCase> { WalletConnectUseCaseImpl(get()) }
    single<UserSession> { UserSessionImpl() }
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
    enableNetworkLogs: Boolean,
): NetworkClientFactory =
    NetworkClientFactory(httpClientEngine, json, tokenManager, enableNetworkLogs)
