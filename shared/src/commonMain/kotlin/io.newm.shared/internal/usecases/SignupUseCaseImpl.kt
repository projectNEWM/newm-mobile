package io.newm.shared.internal.usecases

import io.newm.shared.internal.api.models.NewUser
import io.newm.shared.internal.domainservices.HumanVerificationAction
import io.newm.shared.internal.domainservices.HumanVerificationService
import io.newm.shared.internal.repositories.LogInRepository
import io.newm.shared.internal.usecases.utilities.mapErrorsSuspend
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.SignupUseCase
import kotlin.coroutines.cancellation.CancellationException

internal class SignupUseCaseImpl(
    private val repository: LogInRepository,
    private val humanVerificationService: HumanVerificationService
) : SignupUseCase {

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun requestEmailConfirmationCode(email: String) {
        mapErrorsSuspend {
            val humanVerificationToken = humanVerificationService.verify(HumanVerificationAction.AUTH_CODE)
            repository.requestEmailConfirmationCode(email, humanVerificationToken)
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
            val humanVerificationToken = humanVerificationService.verify(HumanVerificationAction.SIGN_UP)
            repository.registerUser(newUser, humanVerificationToken)
        }
    }
}
