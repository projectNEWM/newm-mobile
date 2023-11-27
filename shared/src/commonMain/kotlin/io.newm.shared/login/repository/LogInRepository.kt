package io.newm.shared.login.repository

import co.touchlab.kermit.Logger
import io.ktor.client.plugins.*
import io.newm.shared.internal.TokenManager
import io.newm.shared.login.models.*
import io.newm.shared.login.models.LoginException.*
import io.newm.shared.login.service.LoginAPI
import io.newm.shared.public.models.error.KMMException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import shared.Notification
import shared.postNotification
import kotlin.coroutines.cancellation.CancellationException

internal class LogInRepository : KoinComponent {
    private val service: LoginAPI by inject()
    private val tokenManager: TokenManager by inject()

    private val logger = Logger.withTag("NewmKMM-LogInRepo")

    @Throws(Exception::class)
    suspend fun requestEmailConfirmationCode(email: String) {
        logger.d { "requestEmailConfirmationCode: email $email" }
        return service.requestEmailConfirmationCode(email)
    }

    suspend fun registerUser(user: NewUser) {
        logger.d { "registerUser: email $user" }
        return service.register(user)
    }

    @Throws(KMMException::class, CancellationException::class)
    suspend fun logIn(email: String, password: String) {
        logger.d { "logIn: email $email" }
        return handleLoginResponse { service.logIn(LogInUser(email = email, password = password)) }
    }

    @Throws(KMMException::class, CancellationException::class)
    suspend fun oAuthLogin(oAuthData: OAuthData) = handleLoginResponse {
        logger.d { "logIn: oAuth" }
        when (oAuthData) {
            is OAuthData.Facebook -> service.loginWithFacebook(FacebookSignInRequest(accessToken = oAuthData.accessToken))
            is OAuthData.Google -> service.loginWithGoogle(GoogleSignInRequest(accessToken = oAuthData.idToken))
            is OAuthData.Apple -> service.loginWithApple(AppleSignInRequest(idToken = oAuthData.idToken))
            is OAuthData.LinkedIn -> service.loginWithLinkedIn(LinkedInSignInRequest(accessToken = oAuthData.accessToken))
        }
    }

    @Throws(KMMException::class, CancellationException::class, WrongPassword::class)
    private suspend fun handleLoginResponse(request: suspend () -> LoginResponse) {
        try {
            storeAccessToken(request())
            postNotification(Notification.loginStateChanged)
        } catch (e: ClientRequestException) {
            when (e.response.status.value) {
                404 -> {
                    logger.d { "logIn: LoginStatus UserNotFound (404)" }
                    //404 NOT FOUND If no registered user with 'email' is found
                    throw UserNotFound("UserNotFoundException")
                }

                401 -> {
                    logger.d { "logIn: LoginStatus WrongPassword (401): $e" }
                    //401 UNAUTHORIZED if 'password' is invalid.
                    throw WrongPassword("WrongPasswordException")
                }

                else -> {
                    // No-Op
                    logger.d { "logIn: LoginStatus: ${e.response.status}" }
                    throw e
                }
            }
        } catch (e: Exception) {
            logger.d { "logIn: LoginStatus 2- UnknownError: $e" }
            throw KMMException("UnknownErrorException")
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    suspend fun registerUser(
        firstName: String,
        lastName: String,
        nickname: String,
        pictureUrl: String,
        email: String,
        newPassword: String,
        confirmPassword: String,
        authCode: String
    ) {
        service.register(
            NewUser(
                firstName = firstName,
                lastName = lastName,
                nickname = nickname,
                pictureUrl = pictureUrl,
                email = email,
                newPassword = newPassword,
                confirmPassword = confirmPassword,
                authCode = authCode
            )
        )
    }

    private fun storeAccessToken(response: LoginResponse) {
        if (response.isValid()) {
            logger.d { "logIn: LoginStatus Valid Response" }
            tokenManager.setAuthTokens(
                response.accessToken.orEmpty(),
                response.refreshToken.orEmpty()
            )
            logger.d { "logIn: LoginStatus Success" }
        } else {
            logger.d { "logIn: Fail to login: $response" }
            throw Exception("Response is not valid: $response")
        }
    }
}
