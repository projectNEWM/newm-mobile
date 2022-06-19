package io.projectnewm.shared.login.service

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.projectnewm.shared.HttpRoutes
import io.projectnewm.shared.login.models.*


interface LogInService {
    suspend fun requestEmailConfirmationCode(email: String): RequestEmailStatus
    suspend fun register(user: NewUser): RegisterStatus
    suspend fun logIn(user: LogInUser): LoginStatus
}

//TODO: Handle Error Cases
internal class LogInServiceImpl(
    private val httpClient: HttpClient
) : LogInService {
    override suspend fun requestEmailConfirmationCode(email: String): RequestEmailStatus {
        return try {
            val response: HttpResponse = httpClient.get {
                url {
                    protocol = HttpRoutes.PROTOCOL
                    host = HttpRoutes.HOST
                    path(HttpRoutes.REQUEST_VERIFICATION_CODE_PATH)
                    parameters.append("email", email)
                }
            }
            if (response.status.value == 204) {
                RequestEmailStatus.Success
            } else {
                RequestEmailStatus.Failure
            }
        } catch (e: RedirectResponseException) {
            // 3XX - responses
            println("LogInService Error (3XX) : ${e.response.status.description}")
            RequestEmailStatus.Failure
        } catch (e: ClientRequestException) {
            // 4XX - responses
            println("LogInService Error (4XX) : ${e.response.status.description}")
            RequestEmailStatus.Failure
        } catch (e: ServerResponseException) {
            // 5XX - responses
            println("LogInService Error (5XX) : ${e.response.status.description}")
            RequestEmailStatus.Failure
        } catch (e: Exception) {
            println("LogInService Error : ${e.message}")
            RequestEmailStatus.Failure
        }
    }

    override suspend fun register(user: NewUser): RegisterStatus {
        return try {
            val response: HttpResponse = httpClient.put<HttpResponse> {
                url {
                    protocol = HttpRoutes.PROTOCOL
                    host = HttpRoutes.HOST
                    path(HttpRoutes.REGISTER_USER_PATH)
                    headers {
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    body = user
                }
            }
            println("LogInService: register user: $user httpResponse $response")
            if (response.status.value == 204) {
                RegisterStatus.Success
            } else {
                RegisterStatus.UnknownError
            }
        } catch (e: ClientRequestException) {
            when (e.response.status.value) {
                409 -> {
                    RegisterStatus.UserAlreadyExists
                }
                403 -> {
                    RegisterStatus.TwoFactorAuthenticationFailed
                }
                else -> {
                    RegisterStatus.UnknownError
                }
            }
        }
    }

    override suspend fun logIn(user: LogInUser): LoginStatus {
        return try {
            val response: HttpResponse = httpClient.post {
                url {
                    protocol = HttpRoutes.PROTOCOL
                    host = HttpRoutes.HOST
                    path(HttpRoutes.LOGIN_PATH)
                    headers {
                        append(HttpHeaders.Accept, "application/json")
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    body = user
                }
            }
            val data: LoginResponse = response.receive()
            LoginStatus.Success(data)
        } catch (e: RedirectResponseException) {
            // 3XX - responses
            println("LogInService Error (3XX) : ${e.response.status.description}")
            LoginStatus.UnknownError
        } catch (e: ClientRequestException) {
            // 4XX - responses
            return when (e.response.status.value) {
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
        } catch (e: ServerResponseException) {
            // 5XX - responses
            println("LogInService Error (5XX) : ${e.response.status.description}")
            LoginStatus.UnknownError
        } catch (e: Exception) {
            println("LogInService Error : ${e.message}")
            LoginStatus.UnknownError
        }
    }
}
