package io.newm.shared.login.service

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.newm.shared.login.models.LogInUser
import io.newm.shared.login.models.LoginResponse
import io.newm.shared.login.models.NewUser
import io.newm.shared.login.models.RegisterException
import io.newm.shared.login.repository.KMMException
import org.koin.core.component.KoinComponent
import kotlin.coroutines.cancellation.CancellationException


internal class LoginAPI(
    private val client: HttpClient
) : KoinComponent {

    @Throws(KMMException::class, CancellationException::class)
    suspend fun requestEmailConfirmationCode(email: String) {
        val response = client.get("/v1/auth/code") {
            contentType(ContentType.Application.Json)
            parameter("email", email)
        }

        return when (response.status) {
            HttpStatusCode.NoContent -> {
            }
            else -> {
                throw KMMException("Unknown Error")
            }
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    suspend fun register(user: NewUser) {
        val response = client.post("/v1/users") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
        when (response.status) {
            HttpStatusCode.OK -> {}
            HttpStatusCode.Conflict -> {
                throw RegisterException.UserAlreadyExists("User already Exists")
            }
            HttpStatusCode.Forbidden -> {
                throw RegisterException.TwoFactorAuthenticationFailed("TwoFactorAuthenticationFailed")
            }
            else -> {
                throw KMMException("Unknown Error")
            }
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    suspend fun logIn(user: LogInUser) = client.post("/v1/auth/login") {
        contentType(ContentType.Application.Json)
        setBody(user)
    }.body<LoginResponse>()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun resetPassword(
        email: String,
        newPassword: String,
        confirmPassword: String,
        authCode: String
    ) = client.put("/v1/users/password") {
        contentType(ContentType.Application.Json)
        parameter("email", email)
        parameter("newPassword", newPassword)
        parameter("confirmPassword", confirmPassword)
        parameter("authCode", authCode)
    }
}