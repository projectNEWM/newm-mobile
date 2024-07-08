package io.newm.shared.internal.repositories

import io.ktor.client.plugins.ClientRequestException
import io.newm.shared.NewmAppLogger
import io.newm.shared.internal.TokenManager
import io.newm.shared.internal.services.db.NewmDatabaseWrapper
import io.newm.shared.internal.repositories.models.OAuthData
import io.newm.shared.internal.api.LoginAPI
import io.newm.shared.internal.api.models.AppleSignInRequest
import io.newm.shared.internal.api.models.FacebookSignInRequest
import io.newm.shared.internal.api.models.GoogleSignInRequest
import io.newm.shared.internal.api.models.LinkedInSignInRequest
import io.newm.shared.internal.api.models.LogInUser
import io.newm.shared.internal.api.models.LoginException.HumanVerificationFailed
import io.newm.shared.internal.api.models.LoginException.UserNotFound
import io.newm.shared.internal.api.models.LoginException.WrongPassword
import io.newm.shared.internal.api.models.LoginResponse
import io.newm.shared.internal.api.models.NewUser
import io.newm.shared.internal.api.models.ResetPasswordRequest
import io.newm.shared.internal.api.models.isValid
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import shared.Notification
import shared.postNotification

internal class LogInRepository : KoinComponent {
    private val service: LoginAPI by inject()
    private val tokenManager: TokenManager by inject()
    private val db: NewmDatabaseWrapper by inject()
    private val logger: NewmAppLogger by inject()


    suspend fun requestEmailConfirmationCode(
        email: String,
        humanVerificationCode: String,
        mustExists: Boolean = false
    ) {
        logger.debug("LogInRepository", "requestEmailConfirmationCode: email $email")
        return service.requestEmailConfirmationCode(email, humanVerificationCode, mustExists)
    }

    suspend fun registerUser(user: NewUser, humanVerificationCode: String) {
        logger.debug("LogInRepository", "registerUser: email $user")
        return service.register(user, humanVerificationCode)
    }

    suspend fun logIn(email: String, password: String, humanVerificationCode: String) {
        logger.debug("LogInRepository", "logIn: email $email")
        return handleLoginResponse {
            service.logIn(
                LogInUser(email = email, password = password),
                humanVerificationCode
            )
        }
    }

    suspend fun oAuthLogin(oAuthData: OAuthData, humanVerificationCode: String) =
        handleLoginResponse {
            logger.debug("LogInRepository", "logIn: oAuth")
            when (oAuthData) {
                is OAuthData.Facebook -> service.loginWithFacebook(FacebookSignInRequest(accessToken = oAuthData.accessToken))
                is OAuthData.Google -> service.loginWithGoogle(
                    GoogleSignInRequest(accessToken = oAuthData.idToken),
                    humanVerificationCode
                )

                is OAuthData.Apple -> service.loginWithApple(
                    AppleSignInRequest(idToken = oAuthData.idToken),
                    humanVerificationCode
                )

                is OAuthData.LinkedIn -> service.loginWithLinkedIn(LinkedInSignInRequest(accessToken = oAuthData.accessToken))
            }
        }

    private suspend fun handleLoginResponse(request: suspend () -> LoginResponse) {
        try {
            storeAccessToken(request())
            postNotification(Notification.loginStateChanged)
        } catch (e: ClientRequestException) {
            logger.error(
                "LogInRepository",
                "LoginStatus 1- ClientRequestException: ${e.response.status}",
                e
            )
            when (e.response.status.value) {
                404 -> {
                    logger.debug("LogInRepository", "logIn: LoginStatus UserNotFound (404)")
                    //404 NOT FOUND If no registered user with 'email' is found
                    throw UserNotFound("Invalid login.  Please try again.")
                }

                401 -> {
                    logger.debug("LogInRepository", "logIn: LoginStatus WrongPassword (401): $e")
                    //401 UNAUTHORIZED if 'password' is invalid.
                    throw WrongPassword("Invalid login.  Please try again.")
                }

                403 -> {
                    logger.debug(
                        "LogInRepository",
                        "logIn: LoginStatus TwoFactorAuthenticationFailed (403): $e"
                    )
                    //403 FORBIDDEN if recaptcha fails.
                    throw HumanVerificationFailed("Humanity could not be verified. Please try again.")
                }

                else -> {
                    // No-Op
                    logger.debug("LogInRepository", "logIn: LoginStatus: ${e.response.status}")
                }
            }
        } catch (e: Exception) {
            logger.error("LogInRepository", "logIn: LoginStatus 2- UnknownError: $e", e)
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
            logger.debug("LogInRepository", "logIn: LoginStatus Valid Response")
            tokenManager.setAuthTokens(
                response.accessToken.orEmpty(),
                response.refreshToken.orEmpty()
            )
        } else {
            logger.debug("LogInRepository", "logIn: Fail to login: $response")
            throw Exception("Response is not valid: $response")
        }
    }

    fun logout() {
        tokenManager.clearToken()
        db.clear()
        postNotification(Notification.loginStateChanged)
    }

    suspend fun resetPassword(
        email: String,
        newPassword: String,
        confirmPassword: String,
        authCode: String,
        humanVerificationCode: String
    ) {
        logger.debug(
            "LogInRepository",
            "resetPassword"
        )
        service.resetPassword(
            ResetPasswordRequest(
                email,
                newPassword,
                confirmPassword,
                authCode,
            ), humanVerificationCode
        )
    }
}
