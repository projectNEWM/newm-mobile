package io.newm.shared.public.usecases

import io.newm.shared.public.models.error.KMMException
import kotlin.coroutines.cancellation.CancellationException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * `SignupUseCase` defines the contract for handling user registration.
 *
 * This interface provides methods for initiating the registration process by requesting
 * an email confirmation code, and for completing the registration with user details.
 */
interface SignupUseCase {

    /**
     * Requests an email confirmation code to be sent to the user's email address.
     * This is typically the first step in the user registration process.
     *
     * @param email The email address where the confirmation code will be sent.
     * @throws KMMException if there is an issue during the email confirmation request process.
     * @throws CancellationException if the request process is cancelled.
     */
    @Throws(KMMException::class, CancellationException::class)
    suspend fun requestEmailConfirmationCode(email: String)

    /**
     * Registers a new user with the provided details.
     * This method completes the user registration process.
     *
     * @param nickname The nickname of the user.
     * @param email The email address of the user.
     * @param password The password chosen by the user.
     * @param passwordConfirmation The password confirmation, should match the `password`.
     * @param verificationCode The code received in the email for confirming the user's email address.
     * @throws KMMException if there is an issue during the registration process.
     * @throws CancellationException if the registration process is cancelled.
     */
    @Throws(KMMException::class, CancellationException::class)
    suspend fun registerUser(
        nickname: String,
        email: String,
        password: String,
        passwordConfirmation: String,
        verificationCode: String
    )
}

class SignupUseCaseProvider() : KoinComponent {
    private val signUpUseCase: SignupUseCase by inject()

    fun get(): SignupUseCase {
        return signUpUseCase
    }
}