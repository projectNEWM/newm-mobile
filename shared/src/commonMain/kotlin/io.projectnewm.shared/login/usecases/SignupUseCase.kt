package io.projectnewm.shared.login.usecases

import io.ktor.http.cio.*
import io.projectnewm.shared.login.models.NewUser
import io.projectnewm.shared.login.models.RegisterError
import io.projectnewm.shared.login.models.RequestEmailError
import io.projectnewm.shared.login.models.RequestEmailStatus
import io.projectnewm.shared.login.repository.LogInRepository
import kotlin.coroutines.cancellation.CancellationException

interface SignupUseCase {
    @Throws(RequestEmailError::class, CancellationException::class)
    suspend fun requestEmailConfirmationCode(email: String)

    @Throws(RegisterError::class, CancellationException::class)
    suspend fun registerUser(
        email: String,
        password: String,
        passwordConfirmation: String,
        verificationCode: String
    )
}

internal class SignupUseCaseImpl(private val repository: LogInRepository) : SignupUseCase {

    @Throws(RequestEmailError::class, CancellationException::class)
    override suspend fun requestEmailConfirmationCode(email: String) {
        return repository.requestEmailConfirmationCode(email)
    }

    @Throws(RegisterError::class, CancellationException::class)
    override suspend fun registerUser(
        email: String,
        password: String,
        passwordConfirmation: String,
        verificationCode: String
    ) {
        val newUser = NewUser(
            email = email,
            newPassword = password,
            confirmPassword = passwordConfirmation,
            authCode = verificationCode
        )
        repository.registerUser(newUser)
    }

}
