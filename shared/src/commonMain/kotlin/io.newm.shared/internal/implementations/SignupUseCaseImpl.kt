package io.newm.shared.internal.implementations

import io.newm.shared.internal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.internal.repositories.LogInRepository
import io.newm.shared.internal.api.models.NewUser
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.SignupUseCase
import kotlin.coroutines.cancellation.CancellationException

internal class SignupUseCaseImpl(private val repository: LogInRepository) : SignupUseCase {

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun requestEmailConfirmationCode(email: String, humanVerificationCode: String, mustExists: Boolean) {
        mapErrorsSuspend {
            repository.requestEmailConfirmationCode(email, humanVerificationCode, mustExists)
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun registerUser(
        email: String,
        password: String,
        passwordConfirmation: String,
        verificationCode: String,
        humanVerificationCode: String
    ) {
        mapErrorsSuspend {
            val newUser = NewUser(
                email = email.trim(),
                newPassword = password.trim(),
                confirmPassword = passwordConfirmation.trim(),
                authCode = verificationCode
            )
            repository.registerUser(newUser, humanVerificationCode)
        }
    }
}
