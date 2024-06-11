package io.newm.shared.internal.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.HttpRequestBuilder
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
import io.newm.shared.internal.api.models.AppleSignInRequest
import io.newm.shared.internal.api.models.FacebookSignInRequest
import io.newm.shared.internal.api.models.GoogleSignInRequest
import io.newm.shared.internal.api.models.LinkedInSignInRequest
import io.newm.shared.internal.api.models.LogInUser
import io.newm.shared.internal.api.models.LoginResponse
import io.newm.shared.internal.api.models.NewUser
import io.newm.shared.internal.api.models.RegisterException
import io.newm.shared.internal.api.models.ResetPasswordException
import io.newm.shared.internal.api.models.ResetPasswordRequest
import io.newm.shared.public.models.error.KMMException
import org.koin.core.component.KoinComponent
import shared.getPlatformName


internal class LoginAPI(
    networkClient: NetworkClientFactory
) : KoinComponent {

    private val httpClient: HttpClient = networkClient.httpClient()

    suspend fun requestEmailConfirmationCode(email: String, humanVerificationCode: String) {
        val response = httpClient.get("/v1/auth/code") {
            contentType(ContentType.Application.Json)
            parameter("email", email)
            addHumanVerificationCodeToHeader(humanVerificationCode)
        }

        return when (response.status) {
            HttpStatusCode.NoContent -> {
            }
            else -> {
                throw KMMException("Unknown Error")
            }
        }
    }

    suspend fun register(user: NewUser, humanVerificationCode: String) {
        val response = httpClient.post("/v1/users") {
            contentType(ContentType.Application.Json)
            setBody(user)
            addHumanVerificationCodeToHeader(humanVerificationCode)
        }
        when (response.status) {
            HttpStatusCode.OK -> {}
            HttpStatusCode.Conflict -> {
                throw RegisterException.UserAlreadyExists("User already exists")
            }
            HttpStatusCode.Forbidden -> {
                throw RegisterException.TwoFactorAuthenticationFailed("TwoFactorAuthenticationFailed")
            }
            else -> {
                throw KMMException("Unknown Error")
            }
        }
    }

    suspend fun logIn(user: LogInUser, humanVerificationCode: String) = httpClient.post("/v1/auth/login") {
        contentType(ContentType.Application.Json)
        setBody(user)
        addHumanVerificationCodeToHeader(humanVerificationCode)
    }.body<LoginResponse>()

    suspend fun loginWithGoogle(request: GoogleSignInRequest, humanVerificationCode: String): LoginResponse {
        val response = httpClient.post("/v1/auth/login/google") {
            contentType(ContentType.Application.Json)
            setBody(request)
            addHumanVerificationCodeToHeader(humanVerificationCode)
        }

        return when (response.status) {
            HttpStatusCode.OK -> response.body<LoginResponse>()
            else -> {
                throw KMMException("HTTP Error ${response.status}: ${response.bodyAsText()}")
            }
        }
    }

    suspend fun loginWithApple(request: AppleSignInRequest, humanVerificationCode: String): LoginResponse {
        val response = httpClient.post("/v1/auth/login/apple") {
            contentType(ContentType.Application.Json)
            setBody(request)
            addHumanVerificationCodeToHeader(humanVerificationCode)
        }

        return when (response.status) {
            HttpStatusCode.OK -> response.body<LoginResponse>()
            else -> {
                throw KMMException("HTTP Error ${response.status}: ${response.bodyAsText()}")
            }
        }
    }

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

    suspend fun resetPassword(request: ResetPasswordRequest, humanVerificationCode: String) {
        try {
            httpClient.put("/v1/users/password") {
                contentType(ContentType.Application.Json)
                setBody(request)
                addHumanVerificationCodeToHeader(humanVerificationCode)
            }
        } catch (e: ClientRequestException) {
            throw when (e.response.status.value) {
                400 -> {
                    ResetPasswordException.MissingField("Missing field")
                }
                403 -> {
                    ResetPasswordException.InvalidAuthCode("Invalid auth code")
                }
                422 -> {
                    ResetPasswordException.InvalidContent("Invalid content")
                }
                404 -> {
                    ResetPasswordException.EmailNotFound("Email not found: ${request.email}")
                }
                else -> {
                    e
                }
            }
        }
    }
}

private fun HttpRequestBuilder.addHumanVerificationCodeToHeader(humanVerificationCode: String) {
    this.headers.append("g-recaptcha-token", humanVerificationCode)
    this.headers.append("g-recaptcha-platform", getPlatformName())
}
