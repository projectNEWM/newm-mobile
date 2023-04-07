package io.newm.shared.login.repository

import co.touchlab.kermit.Logger
import io.ktor.client.plugins.*
import io.newm.shared.db.NewmDatabaseWrapper
import io.newm.shared.login.models.*
import io.newm.shared.login.service.NewmApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface LogInRepository {
    suspend fun requestEmailConfirmationCode(email: String): RequestEmailStatus
    suspend fun registerUser(user: NewUser): RegisterStatus
    suspend fun logIn(email: String, password: String): LoginStatus
    suspend fun registerUser(
        firstName: String,
        lastName: String,
        nickname: String,
        pictureUrl: String,
        email: String,
        newPassword: String,
        confirmPassword: String,
        authCode: String
    ): RegisterStatus
}

internal class LogInRepositoryImpl : KoinComponent, LogInRepository {
    private val service: NewmApi by inject()
    private val db: NewmDatabaseWrapper by inject()

    private val logger = Logger.withTag("NewmKMM-LogInRepo")

    override suspend fun requestEmailConfirmationCode(email: String): RequestEmailStatus {
        logger.d { "requestEmailConfirmationCode: email $email" }
        return service.requestEmailConfirmationCode(email)
    }

    override suspend fun registerUser(user: NewUser): RegisterStatus {
        logger.d { "registerUser: email $user" }
        return service.register(user)
    }

    override suspend fun logIn(email: String, password: String): LoginStatus {
        logger.d { "logIn: email $email" }
        return try {
            val response: LoginResponse =
                service.logIn(LogInUser(email = email, password = password))

            return if (response.isValid()) {
                val auth = db.instance?.newmAuthQueries
                auth?.insertAuthData(1, response.accessToken.orEmpty(), response.refreshToken.orEmpty())
                logger.d { "logIn: LoginStatus Success" }
                LoginStatus.Success(response)
            } else {
                logger.d { "logIn: LoginStatus UnknownError" }
                LoginStatus.UnknownError
            }
        } catch (e: ClientRequestException) {
            when (e.response.status.value) {
                404 -> {
                    logger.d { "logIn: LoginStatus UserNotFound (404)" }
                    //404 NOT FOUND If no registered user with 'email' is found
                    LoginStatus.UserNotFound
                }
                401 -> {
                    logger.d { "logIn: LoginStatus WrongPassword (401): $e" }
                    //401 UNAUTHORIZED if 'password' is invalid.
                    LoginStatus.WrongPassword
                }
                else -> {
                    logger.d { "logIn: LoginStatus 1 -UnknownError: $e" }
                    LoginStatus.UnknownError
                }
            }
        } catch (e: Exception) {
            logger.d { "logIn: LoginStatus 2- UnknownError: $e" }
            LoginStatus.UnknownError
        }
    }

    override suspend fun registerUser(
        firstName: String,
        lastName: String,
        nickname: String,
        pictureUrl: String,
        email: String,
        newPassword: String,
        confirmPassword: String,
        authCode: String
    ): RegisterStatus {
        return service.register(
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
}