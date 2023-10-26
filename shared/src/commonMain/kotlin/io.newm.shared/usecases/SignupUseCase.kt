package io.newm.shared.usecases

import io.newm.shared.login.models.NewUser
import io.newm.shared.login.repository.KMMException
import io.newm.shared.login.repository.LogInRepository
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

internal class SignupUseCaseImpl(private val repository: LogInRepository) : SignupUseCase {

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun requestEmailConfirmationCode(email: String) {
        return repository.requestEmailConfirmationCode(email)
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun registerUser(
        nickname: String,
        email: String,
        password: String,
        passwordConfirmation: String,
        verificationCode: String
    ) {
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

class SignupUseCaseProvider(): KoinComponent {
    private val signUpUseCase: SignupUseCase by inject()

    fun get(): SignupUseCase {
        return signUpUseCase
    }
}