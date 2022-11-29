package io.projectnewm.shared.di

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import io.projectnewm.shared.login.repository.LogInRepository
import io.projectnewm.shared.login.repository.LogInRepositoryImpl
import io.projectnewm.shared.login.service.NewmApi
import io.projectnewm.shared.login.usecases.LoginUseCase
import io.projectnewm.shared.login.usecases.LoginUseCaseImpl
import io.projectnewm.shared.login.usecases.SignupUseCase
import io.projectnewm.shared.login.usecases.SignupUseCaseImpl
import io.projectnewm.shared.platformModule
import kotlinx.serialization.json.Json

fun initKoin(enableNetworkLogs: Boolean = true, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(enableNetworkLogs = enableNetworkLogs), platformModule())
    }

// called by iOS etc
//https://johnoreilly.dev/posts/kotlinmultiplatform-koin/
fun initKoin() = initKoin(enableNetworkLogs = false) {}

fun commonModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single { createHttpClient(get(), enableNetworkLogs = enableNetworkLogs) }
    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }

    single { NewmApi(get()) }
    single<SignupUseCase> { SignupUseCaseImpl(get()) }
    single<LoginUseCase> { LoginUseCaseImpl(get()) }
    single<LogInRepository> { LogInRepositoryImpl() }
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }

fun createHttpClient(json: Json, enableNetworkLogs: Boolean) =
    HttpClient(CIO) {
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