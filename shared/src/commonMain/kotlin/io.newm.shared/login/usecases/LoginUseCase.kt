package io.newm.shared.login.usecases

import io.newm.shared.login.models.LoginStatus
import io.newm.shared.login.repository.LogInRepository

interface LoginUseCase {
    suspend fun logIn(email: String, password: String): LoginStatus
}

internal class LoginUseCaseImpl(private val repository: LogInRepository) : LoginUseCase {
    override suspend fun logIn(email: String, password: String): LoginStatus {
        return repository.logIn(email = email, password = password)
    }
}