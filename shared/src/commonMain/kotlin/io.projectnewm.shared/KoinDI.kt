package io.projectnewm.shared

import io.projectnewm.shared.login.repository.LogInRepository
import io.projectnewm.shared.login.service.LogInService
import io.projectnewm.shared.login.service.LogInServiceImpl
import io.projectnewm.shared.login.usecases.LoginUseCase
import io.projectnewm.shared.login.usecases.LoginUseCaseImpl
import io.projectnewm.shared.login.usecases.SignupUseCase
import io.projectnewm.shared.login.usecases.SignupUseCaseImpl
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(commonModule)
}

// called by iOS etc
//https://johnoreilly.dev/posts/kotlinmultiplatform-koin/
fun initKoin() = initKoin {}

internal val commonModule = module {
    single<SignupUseCase> { SignupUseCaseImpl(get()) }
    single<LoginUseCase> { LoginUseCaseImpl(get()) }
    single { LogInRepository(get()) }
    single<LogInService> { LogInServiceImpl(get()) }
}