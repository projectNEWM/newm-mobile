package io.newm.shared.internal.useCases

import io.newm.shared.internal.repositories.LogInRepository
import io.newm.shared.internal.services.models.NewUser
import io.newm.shared.internal.useCases.utilities.mapErrorsSuspend
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.SignupUseCase
import kotlin.coroutines.cancellation.CancellationException

internal class SignupUseCaseImpl(private val repository: LogInRepository) : SignupUseCase {

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun requestEmailConfirmationCode(email: String) {
        mapErrorsSuspend {
            repository.requestEmailConfirmationCode(email)
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun registerUser(
        nickname: String,
        email: String,
        password: String,
        passwordConfirmation: String,
        verificationCode: String
    ) {
        mapErrorsSuspend {
            val newUser = NewUser(
                nickname = nickname,
                email = email,
                newPassword = password,
                confirmPassword = passwordConfirmation,
                authCode = verificationCode
            )
            repository.registerUser(newUser)
        }
    }
}
