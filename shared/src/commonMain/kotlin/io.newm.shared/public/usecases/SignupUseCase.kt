package io.newm.shared.public.usecases

import io.newm.shared.public.models.error.KMMException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

interface SignupUseCase {
    @Throws(KMMException::class, CancellationException::class)
    suspend fun requestEmailConfirmationCode(email: String)

    @Throws(KMMException::class, CancellationException::class)
    suspend fun registerUser(
        nickname: String,
        email: String,
        password: String,
        passwordConfirmation: String,
        verificationCode: String
    )
}

class SignupUseCaseProvider(): KoinComponent {
    private val signUpUseCase: SignupUseCase by inject()

    fun get(): SignupUseCase {
        return signUpUseCase
    }
}