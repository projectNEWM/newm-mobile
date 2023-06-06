package io.newm.shared.di

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import io.newm.shared.HttpRoutes
import io.newm.shared.login.UserSession
import io.newm.shared.login.UserSessionImpl
import io.newm.shared.login.repository.LogInRepository
import io.newm.shared.login.repository.LogInRepositoryImpl
import io.newm.shared.login.service.LoginAPI
import io.newm.shared.login.usecases.LoginUseCase
import io.newm.shared.login.usecases.LoginUseCaseImpl
import io.newm.shared.login.usecases.SignupUseCase
import io.newm.shared.login.usecases.SignupUseCaseImpl
import io.newm.shared.repositories.*
import io.newm.shared.repositories.GenresRepositoryImpl
import io.newm.shared.repositories.PlaylistRepositoryImpl
import io.newm.shared.repositories.UserRepository
import io.newm.shared.repositories.UserRepositoryImpl
import io.newm.shared.services.GenresAPI
import io.newm.shared.services.PlaylistAPI
import io.newm.shared.services.UserAPI
import io.newm.shared.usecases.GetGenresUseCase
import io.newm.shared.usecases.GetGenresUseCaseImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
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
    single { createHttpClient(get(), get(), enableNetworkLogs = enableNetworkLogs) }
    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }
    // Internal API Services
    single { LoginAPI(get()) }
    single { GenresAPI(get()) }
    single { UserAPI(get()) }
    single { PlaylistAPI(get()) }
    // Internal Repositories
    single<LogInRepository> { LogInRepositoryImpl() }
    single<GenresRepository> { GenresRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }
    single<PlaylistRepository> { PlaylistRepositoryImpl() }
    // External Use Cases
    single<LoginUseCase> { LoginUseCaseImpl(get()) }
    single<SignupUseCase> { SignupUseCaseImpl(get()) }
    single<GetGenresUseCase> { GetGenresUseCaseImpl(get()) }
    single<UserSession> { UserSessionImpl() }
}

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    encodeDefaults = true
}

fun createHttpClient(httpClientEngine: HttpClientEngine, json: Json, enableNetworkLogs: Boolean) =
    HttpClient(httpClientEngine) {
        defaultRequest {
            url(HttpRoutes.HOST)
        }
        install(ContentNegotiation) {
            json(json)
        }
        if (enableNetworkLogs) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

class LoginUseCaseFactory() : KoinComponent {
    private val loginUseCase: LoginUseCase by inject()

    @Throws(Exception::class)
    fun loginUseCase(): LoginUseCase {
        return loginUseCase
    }
}

class SignupUseCaseFactory() : KoinComponent {
    private val signupUseCase: SignupUseCase by inject()

    @Throws(Exception::class)
    fun signupUseCase(): SignupUseCase {
        return signupUseCase
    }
}

class UserSessionFactory() : KoinComponent {
    private val userSession: UserSession by inject()

    @Throws(Exception::class)
    fun userSession(): UserSession {
        return userSession
    }
}

class GetGenresUseCaseFactory() : KoinComponent {
    private val getGenresUseCase: GetGenresUseCase by inject()

    @Throws(Exception::class)
    fun getGenresUseCase(): GetGenresUseCase {
        return getGenresUseCase
    }
}