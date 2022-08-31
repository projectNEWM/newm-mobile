package io.projectnewm.shared

import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.*
import io.projectnewm.shared.login.models.RegisterError
import io.projectnewm.shared.login.repository.LogInRepository
import io.projectnewm.shared.login.service.LogInService
import io.projectnewm.shared.login.service.LogInServiceImpl
import io.projectnewm.shared.login.usecases.LoginUseCase
import io.projectnewm.shared.login.usecases.LoginUseCaseImpl
import io.projectnewm.shared.login.usecases.SignupUseCase
import io.projectnewm.shared.login.usecases.SignupUseCaseImpl
import io.projectnewm.shared.repository.db.NewmDatabaseFactory
import io.projectnewm.shared.repository.db.NewmDb
import io.projectnewm.shared.repository.db.SqlDelightDriverFactory
//import io.projectnewm.shared.users.UserDAO
//import io.projectnewm.shared.users.UserDAOImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(commonModule, networkModule)
}

// called by iOS etc
//https://johnoreilly.dev/posts/kotlinmultiplatform-koin/
fun initKoin() = initKoin {}

val commonModule = module {
    factory<SignupUseCase> { SignupUseCaseImpl(get()) }
    factory<LoginUseCase> { LoginUseCaseImpl(get()) }
    factory { LogInRepository(get()) }
    factory<LogInService> { LogInServiceImpl(get()) }
    factory<HttpClient> { HttpClient() }
//    factory<UserDAO> { UserDAOImpl(get()) }
//    factory<NewmDb> { NewmDatabaseFactory(get()).createDatabase() }
//    factory<SqlDriver> { SqlDelightDriverFactory().createDriver }
}

val networkModule = module {
    single { KtorClientFactory().build() }
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

    @Throws(RegisterError::class)
    fun signupUseCase(): SignupUseCase {
        return signupUseCase
    }
}