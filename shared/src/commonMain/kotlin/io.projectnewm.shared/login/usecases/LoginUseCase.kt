package io.projectnewm.shared.login.usecases

import io.projectnewm.shared.login.repository.LogInRepository

interface LoginUseCase {
    @Throws(Throwable::class)
    suspend fun logIn(email: String, password: String)
}

internal class LoginUseCaseImpl(private val repository: LogInRepository) : LoginUseCase {
    @Throws(Throwable::class)
    override suspend fun logIn(email: String, password: String) {
        repository.logIn(email = email, password = password)
    }
}