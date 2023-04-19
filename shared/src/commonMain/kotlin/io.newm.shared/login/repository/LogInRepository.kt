package io.newm.shared.login.repository

import co.touchlab.kermit.Logger
import io.ktor.client.plugins.*
import io.newm.shared.db.NewmDatabaseWrapper
import io.newm.shared.login.models.LogInUser
import io.newm.shared.login.models.LoginResponse
import io.newm.shared.login.models.NewUser
import io.newm.shared.login.models.isValid
import io.newm.shared.login.service.NewmApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

class KMMException(message: String) : Throwable(message)

interface LogInRepository {
    @Throws(Exception::class)
    suspend fun requestEmailConfirmationCode(email: String)
    @Throws(Exception::class)
    suspend fun registerUser(user: NewUser)
    @Throws(KMMException::class, CancellationException::class)
    suspend fun logIn(email: String, password: String)
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
    )
}

internal class LogInRepositoryImpl : KoinComponent, LogInRepository {
    private val service: NewmApi by inject()
    private val db: NewmDatabaseWrapper by inject()

    private val logger = Logger.withTag("NewmKMM-LogInRepo")

    @Throws(Exception::class)
    override suspend fun requestEmailConfirmationCode(email: String) {
        logger.d { "requestEmailConfirmationCode: email $email" }
        return service.requestEmailConfirmationCode(email)
    }

    override suspend fun registerUser(user: NewUser) {
        logger.d { "registerUser: email $user" }
        return service.register(user)
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logIn(email: String, password: String) {
        logger.d { "logIn: email $email" }
        return try {
            val response: LoginResponse =
                service.logIn(LogInUser(email = email, password = password))

            if (response.isValid()) {
                val auth = db.instance?.newmAuthQueries
                auth?.insertAuthData(1, response.accessToken.orEmpty(), response.refreshToken.orEmpty())
                logger.d { "logIn: LoginStatus Success" }
            } else {
                logger.d { "logIn: LoginStatus UnknownError" }
                throw KMMException("UnknownErrorException")
            }
        } catch (e: ClientRequestException) {
            when (e.response.status.value) {
                404 -> {
                    logger.d { "logIn: LoginStatus UserNotFound (404)" }
                    //404 NOT FOUND If no registered user with 'email' is found
                    throw KMMException("UserNotFoundException")
                }
                401 -> {
                    logger.d { "logIn: LoginStatus WrongPassword (401): $e" }
                    //401 UNAUTHORIZED if 'password' is invalid.
                    throw KMMException("WrongPasswordException")
                }
                else -> {
                    logger.d { "logIn: LoginStatus 1 -UnknownError: $e" }
                    throw KMMException("UnknownErrorException")
                }
            }
        } catch (e: Exception) {
            logger.d { "logIn: LoginStatus 2- UnknownError: $e" }
            throw KMMException("UnknownErrorException")
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun registerUser(
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
}