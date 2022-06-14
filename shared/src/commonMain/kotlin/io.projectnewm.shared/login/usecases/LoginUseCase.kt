package io.projectnewm.shared.login.usecases

import io.projectnewm.shared.login.repository.LogInRepository

interface LoginUseCase {
    suspend fun logIn(email: String, password: String): Boolean
}

internal class LoginUseCaseImpl(private val repository: LogInRepository) : LoginUseCase {
    override suspend fun logIn(email: String, password: String): Boolean {
        return repository.logIn(email = email, password = password)
    }
}