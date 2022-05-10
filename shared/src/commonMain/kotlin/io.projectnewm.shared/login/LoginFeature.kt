package io.projectnewm.shared.login

import io.ktor.client.*
import io.projectnewm.shared.KtorClientFactory

object LoginConfig : LoginFeature {
    private val httpClient: HttpClient = KtorClientFactory().build()
    private val service: LogInService = LogInServiceImpl(httpClient)
    private val repository: LogInRepository = LogInRepository(service)
    private val useCase: LoginUseCase = LoginUseCaseImpl(repository)

    override fun getLoginUseCase(): LoginUseCase {
        return useCase
    }
}

interface LoginFeature {
    fun getLoginUseCase(): LoginUseCase
}