package io.newm.shared.login.usecases

import io.newm.shared.login.repository.KMMException
import io.newm.shared.login.repository.LogInRepository
import kotlin.coroutines.cancellation.CancellationException

interface LoginUseCase {
    @Throws(KMMException::class, CancellationException::class)
    suspend fun logIn(email: String, password: String)
}

internal class LoginUseCaseImpl(private val repository: LogInRepository) : LoginUseCase {
    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logIn(email: String, password: String) {
        return repository.logIn(email = email, password = password)
    }
}