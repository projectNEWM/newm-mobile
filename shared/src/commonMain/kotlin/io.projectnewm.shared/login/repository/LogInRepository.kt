package io.projectnewm.shared.login.repository

import io.ktor.client.plugins.*
import io.projectnewm.shared.login.models.*
import io.projectnewm.shared.login.service.NewmApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface LogInRepository {
    suspend fun requestEmailConfirmationCode(email: String): RequestEmailStatus
    suspend fun registerUser(user: NewUser): RegisterStatus
    suspend fun logIn(email: String, password: String): LoginStatus
    suspend fun registerUser(
        firstName: String,
        lastName: String,
        pictureUrl: String,
        email: String,
        newPassword: String,
        confirmPassword: String,
        authCode: String
    ): RegisterStatus
}

internal class LogInRepositoryImpl : KoinComponent, LogInRepository {

    private val service: NewmApi by inject()

    override suspend fun requestEmailConfirmationCode(email: String): RequestEmailStatus {
        return service.requestEmailConfirmationCode(email)
    }

    override suspend fun registerUser(user: NewUser): RegisterStatus {
        return service.register(user)
    }

    override suspend fun logIn(email: String, password: String): LoginStatus {
        return try {
            val response: LoginResponse =
                service.logIn(LogInUser(email = email, password = password))
            LoginStatus.Success(response)
        } catch (e: ClientRequestException) {
            when (e.response.status.value) {
                404 -> {
                    //404 NOT FOUND If no registered user with 'email' is found
                    LoginStatus.UserNotFound
                }
                401 -> {
                    //401 UNAUTHORIZED if 'password' is invalid.
                    LoginStatus.WrongPassword
                }
                else -> {
                    LoginStatus.UnknownError
                }
            }
        } catch (e: Exception) {
            LoginStatus.UnknownError
        }
    }

    override suspend fun registerUser(
        firstName: String,
        lastName: String,
        pictureUrl: String,
        email: String,
        newPassword: String,
        confirmPassword: String,
        authCode: String
    ): RegisterStatus {
        return service.register(
            NewUser(
                firstName,
                lastName,
                pictureUrl,
                email,
                newPassword,
                confirmPassword,
                authCode
            )
        )
    }
}