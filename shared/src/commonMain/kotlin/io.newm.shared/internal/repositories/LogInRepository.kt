package io.newm.shared.internal.repositories

import co.touchlab.kermit.Logger
import io.ktor.client.plugins.ClientRequestException
import io.newm.shared.internal.TokenManager
import io.newm.shared.internal.db.NewmDatabaseWrapper
import io.newm.shared.internal.repositories.models.OAuthData
import io.newm.shared.internal.services.LoginAPI
import io.newm.shared.internal.services.models.AppleSignInRequest
import io.newm.shared.internal.services.models.FacebookSignInRequest
import io.newm.shared.internal.services.models.GoogleSignInRequest
import io.newm.shared.internal.services.models.LinkedInSignInRequest
import io.newm.shared.internal.services.models.LogInUser
import io.newm.shared.internal.services.models.LoginException.HumanVerificationFailed
import io.newm.shared.internal.services.models.LoginException.UserNotFound
import io.newm.shared.internal.services.models.LoginException.WrongPassword
import io.newm.shared.internal.services.models.LoginResponse
import io.newm.shared.internal.services.models.NewUser
import io.newm.shared.internal.services.models.ResetPasswordRequest
import io.newm.shared.internal.services.models.isValid
import io.newm.shared.public.models.error.KMMException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import shared.Notification
import shared.postNotification

internal class LogInRepository : KoinComponent {
    private val service: LoginAPI by inject()
    private val tokenManager: TokenManager by inject()
    private val db: NewmDatabaseWrapper by inject()
    private val logger = Logger.withTag("NewmKMM-LogInRepo")


    suspend fun requestEmailConfirmationCode(email: String, humanVerificationCode: String) {
        logger.d { "requestEmailConfirmationCode: email $email" }
        return service.requestEmailConfirmationCode(email, humanVerificationCode)
    }

    suspend fun registerUser(user: NewUser, humanVerificationCode: String) {
        logger.d { "registerUser: email $user" }
        return service.register(user, humanVerificationCode)
    }

    suspend fun logIn(email: String, password: String, humanVerificationCode: String) {
        logger.d { "logIn: email $email" }
        return handleLoginResponse { service.logIn(LogInUser(email = email, password = password), humanVerificationCode) }
    }

    suspend fun oAuthLogin(oAuthData: OAuthData, humanVerificationCode: String) = handleLoginResponse {
        logger.d { "logIn: oAuth" }
        when (oAuthData) {
            is OAuthData.Facebook -> service.loginWithFacebook(FacebookSignInRequest(accessToken = oAuthData.accessToken))
            is OAuthData.Google -> service.loginWithGoogle(GoogleSignInRequest(accessToken = oAuthData.idToken), humanVerificationCode)
            is OAuthData.Apple -> service.loginWithApple(AppleSignInRequest(idToken = oAuthData.idToken), humanVerificationCode)
            is OAuthData.LinkedIn -> service.loginWithLinkedIn(LinkedInSignInRequest(accessToken = oAuthData.accessToken))
        }
    }

    private suspend fun handleLoginResponse(request: suspend () -> LoginResponse) {
        try {
            storeAccessToken(request())
            postNotification(Notification.loginStateChanged)
        } catch (e: ClientRequestException) {
            when (e.response.status.value) {
                404 -> {
                    logger.d { "logIn: LoginStatus UserNotFound (404)" }
                    //404 NOT FOUND If no registered user with 'email' is found
                    throw UserNotFound("Invalid login.  Please try again.")
                }

                401 -> {
                    logger.d { "logIn: LoginStatus WrongPassword (401): $e" }
                    //401 UNAUTHORIZED if 'password' is invalid.
                    throw WrongPassword("Invalid login.  Please try again.")
                }

                403 -> {
                    logger.d { "logIn: LoginStatus TwoFactorAuthenticationFailed (403): $e" }
                    //403 FORBIDDEN if recaptcha fails.
                    throw HumanVerificationFailed("Humanity could not be verified. Please try again.")
                }

                else -> {
                    // No-Op
                    logger.d { "logIn: LoginStatus: ${e.response.status}" }
                    throw e
                }
            }
        } catch (e: Exception) {
            logger.e { "logIn: LoginStatus 2- UnknownError: $e" }
            throw KMMException("UnknownErrorException")
        }
    }

    suspend fun registerUser(
        firstName: String,
        lastName: String,
        nickname: String,
        pictureUrl: String,
        email: String,
        newPassword: String,
        confirmPassword: String,
        authCode: String,
        humanVerificationCode: String
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
            ), humanVerificationCode
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

    fun logout() {
        tokenManager.clearToken()
        db.clear()
    }

    suspend fun resetPassword(email: String, newPassword: String, confirmPassword: String, authCode: String, humanVerificationCode: String) {
        logger.d { "resetPassword" }
        service.resetPassword(ResetPasswordRequest(
            email,
            newPassword,
            confirmPassword,
            authCode,
        ), humanVerificationCode)
    }
}
