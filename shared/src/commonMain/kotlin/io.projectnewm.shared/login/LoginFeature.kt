package io.projectnewm.shared.login

import io.ktor.client.*
import io.projectnewm.shared.KtorClientFactory

object LoginFeature {
    private val httpClient: HttpClient = KtorClientFactory().build()
    private val service: LogInService = LogInServiceImpl(httpClient)
    private val repository: LogInRepository = LogInRepository(service)

    val useCase: LoginUseCase = LoginUseCase(repository)
}