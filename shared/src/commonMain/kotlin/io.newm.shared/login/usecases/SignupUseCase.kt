package io.newm.shared.login.usecases

import io.newm.shared.login.models.NewUser
import io.newm.shared.login.models.RegisterStatus
import io.newm.shared.login.models.RequestEmailStatus
import io.newm.shared.login.repository.LogInRepository

interface SignupUseCase {
    suspend fun requestEmailConfirmationCode(email: String): RequestEmailStatus

    suspend fun registerUser(
        email: String,
        password: String,
        passwordConfirmation: String,
        verificationCode: String
    ): RegisterStatus
}

internal class SignupUseCaseImpl(private val repository: LogInRepository) : SignupUseCase {

    override suspend fun requestEmailConfirmationCode(email: String): RequestEmailStatus {
        return repository.requestEmailConfirmationCode(email)
    }

    override suspend fun registerUser(
        email: String,
        password: String,
        passwordConfirmation: String,
        verificationCode: String
    ): RegisterStatus {
        val newUser = NewUser(
            email = email,
            newPassword = password,
            confirmPassword = passwordConfirmation,
            authCode = verificationCode
        )
        return repository.registerUser(newUser)
    }

}
