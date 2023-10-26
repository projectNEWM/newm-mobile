package io.newm.shared.login.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.newm.shared.di.NetworkClientFactory
import io.newm.shared.login.models.AppleSignInRequest
import io.newm.shared.login.models.FacebookSignInRequest
import io.newm.shared.login.models.GoogleSignInRequest
import io.newm.shared.login.models.LinkedInSignInRequest
import io.newm.shared.login.models.LogInUser
import io.newm.shared.login.models.LoginResponse
import io.newm.shared.login.models.NewUser
import io.newm.shared.login.models.RegisterException
import io.newm.shared.login.repository.KMMException
import org.koin.core.component.KoinComponent
import kotlin.coroutines.cancellation.CancellationException


internal class LoginAPI(
    networkClient: NetworkClientFactory
) : KoinComponent {

    private val httpClient: HttpClient = networkClient.httpClient()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun requestEmailConfirmationCode(email: String) {
        val response = httpClient.get("/v1/auth/code") {
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
        val response = httpClient.post("/v1/users") {
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
    suspend fun logIn(user: LogInUser) = httpClient.post("/v1/auth/login") {
        contentType(ContentType.Application.Json)
        setBody(user)
    }.body<LoginResponse>()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun loginWithGoogle(request: GoogleSignInRequest): LoginResponse {
        val response = httpClient.post("/v1/auth/login/google") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        return when (response.status) {
            HttpStatusCode.OK -> response.body<LoginResponse>()
            else -> {
                throw KMMException("HTTP Error ${response.status}: ${response.bodyAsText()}")
            }
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    suspend fun loginWithApple(request: AppleSignInRequest): LoginResponse {
        val response = httpClient.post("/v1/auth/login/apple") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        return when (response.status) {
            HttpStatusCode.OK -> response.body<LoginResponse>()
            else -> {
                throw KMMException("HTTP Error ${response.status}: ${response.bodyAsText()}")
            }
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    suspend fun loginWithFacebook(request: FacebookSignInRequest): LoginResponse {
        val response = httpClient.post("/v1/auth/login/facebook") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        return when (response.status) {
            HttpStatusCode.OK -> response.body<LoginResponse>()
            else -> {
                throw KMMException("HTTP Error ${response.status}: ${response.bodyAsText()}")
            }
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    suspend fun loginWithLinkedIn(request: LinkedInSignInRequest): LoginResponse {
        val response = httpClient.post("/v1/auth/login/linkedin") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        return when (response.status) {
            HttpStatusCode.OK -> response.body<LoginResponse>()
            else -> {
                throw KMMException("HTTP Error ${response.status}: ${response.bodyAsText()}")
            }
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    suspend fun resetPassword(
        email: String,
        newPassword: String,
        confirmPassword: String,
        authCode: String
    ) = httpClient.put("/v1/users/password") {
        contentType(ContentType.Application.Json)
        parameter("email", email)
        parameter("newPassword", newPassword)
        parameter("confirmPassword", confirmPassword)
        parameter("authCode", authCode)
    }
}
