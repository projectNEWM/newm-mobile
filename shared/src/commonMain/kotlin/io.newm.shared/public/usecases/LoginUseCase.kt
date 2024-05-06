package io.newm.shared.public.usecases

import io.newm.shared.public.models.error.KMMException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

/**
 * `LoginUseCase` defines the contract for handling user authentication in the Newm mobile application.
 *
 * This interface provides methods for logging in using various authentication methods,
 * including email/password and third-party providers such as Google, Facebook, LinkedIn, and Apple.
 * It also offers functionality to log out.
 */
interface LoginUseCase {
    sealed class LoginException() {
        data class invalidLogin(override val message: String) : KMMException(message)
    }
    /**
     * Authenticates a user using their email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @throws KMMException if there is an issue during the login process.
     * @throws CancellationException if the login process is cancelled.
     */
    @Throws(KMMException::class, CancellationException::class)
    suspend fun logIn(email: String, password: String, humanVerificationCode: String)

    /**
     * Authenticates a user using their Google account.
     *
     * @param idToken The ID token from Google Sign-In.
     * @throws KMMException if there is an issue during the login process.
     * @throws CancellationException if the login process is cancelled.
     */
    @Throws(KMMException::class, CancellationException::class)
    suspend fun logInWithGoogle(idToken: String, humanVerificationCode: String)

    /**
     * Authenticates a user using their Facebook account with the given access token.
     *
     * @param accessToken The access token from Facebook Login.
     * @throws KMMException if there is an issue during the login process.
     * @throws CancellationException if the login process is cancelled.
     */

    @Throws(KMMException::class, CancellationException::class)
    suspend fun logInWithFacebook(accessToken: String)

    /**
     * Authenticates a user using their LinkedIn account with the specified access token.
     *
     * @param accessToken The access token from LinkedIn Sign-In.
     * @throws KMMException if there is an issue during the login process.
     * @throws CancellationException if the login process is cancelled.
     */
    @Throws(KMMException::class, CancellationException::class)
    suspend fun logInWithLinkedIn(accessToken: String)

    /**
     * Authenticates a user using their Apple ID with the provided ID token.
     *
     * @param idToken The ID token from Apple Sign-In.
     * @throws KMMException if there is an issue during the login process.
     * @throws CancellationException if the login process is cancelled.
     */
    @Throws(KMMException::class, CancellationException::class)
    suspend fun logInWithApple(idToken: String, humanVerificationCode: String)


    /**
     * Logs out the user.
     *
     * This method should handle all necessary steps to effectively terminate the user's session,
     * such as clearing session tokens, disconnecting from external services, or restoring app state.
     */
    suspend fun logout()
}



class LoginUseCaseProvider(): KoinComponent {
    private val loginUseCase: LoginUseCase by inject()

    fun get(): LoginUseCase {
        return this.loginUseCase
    }
}