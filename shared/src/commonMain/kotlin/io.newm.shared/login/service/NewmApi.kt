package io.newm.shared.login.service

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.newm.shared.login.models.*
import org.koin.core.component.KoinComponent


internal class NewmApi(
    private val client: HttpClient
) : KoinComponent {

    suspend fun requestEmailConfirmationCode(email: String): RequestEmailStatus {
        val response = client.get("/v1/auth/code") {
            contentType(ContentType.Application.Json)
            parameter("email", email)
        }

        return when (response.status) {
            HttpStatusCode.NoContent -> {
                RequestEmailStatus.Success
            }
            else -> {
                RequestEmailStatus.Failure
            }
        }
    }

    suspend fun register(user: NewUser): RegisterStatus {
        val response = client.put("/v1/users") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
        return when (response.status) {
            HttpStatusCode.NoContent -> {
                RegisterStatus.Success
            }
            HttpStatusCode.Conflict -> {
                RegisterStatus.UserAlreadyExists
            }
            HttpStatusCode.Forbidden -> {
                RegisterStatus.TwoFactorAuthenticationFailed
            }
            else -> {
                RegisterStatus.UnknownError
            }
        }
    }

    suspend fun logIn(user: LogInUser) = client.post("/v1/auth/login") {
        contentType(ContentType.Application.Json)
        setBody(user)
    }.body<LoginResponse>()
}