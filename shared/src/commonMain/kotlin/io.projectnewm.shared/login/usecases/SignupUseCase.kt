package io.projectnewm.shared.login.usecases

import io.projectnewm.shared.login.models.NewUser
import io.projectnewm.shared.login.models.RegisterStatus
import io.projectnewm.shared.login.repository.LogInRepository

interface SignupUseCase {
    suspend fun requestEmailConfirmationCode(email: String): String

    suspend fun registerUser(
        email: String,
        password: String,
        passwordConfirmation: String,
        verificationCode: String
    ): RegisterStatus
}

internal class SignupUseCaseImpl(private val repository: LogInRepository) : SignupUseCase {

    override suspend fun requestEmailConfirmationCode(email: String): String {
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
