package io.projectnewm.shared.login.service

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.projectnewm.shared.HttpRoutes
import io.projectnewm.shared.login.models.LogInUser
import io.projectnewm.shared.login.models.NewUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


interface LogInService {
    suspend fun requestEmailConfirmationCode(email: String): String
    suspend fun register(user: NewUser): String
    suspend fun logIn(user: LogInUser): LoginResponse?
}

//TODO: Handle Error Cases
internal class LogInServiceImpl(
    private val httpClient: HttpClient
) : LogInService {
    override suspend fun requestEmailConfirmationCode(email: String): String {
        return try {
            httpClient.get {
                url {
                    protocol = HttpRoutes.PROTOCOL
                    host = HttpRoutes.HOST
                    path(HttpRoutes.REQUEST_VERIFICATION_CODE_PATH)
                    parameters.append("email", email)
                }
//                url("${HttpRoutes.REQUEST_CODE}?email=$email")
            }
        } catch (e: RedirectResponseException) {
            // 3XX - responses
            println("cje466 Error (3XX) : ${e.response.status.description}")
            ""
        } catch (e: ClientRequestException) {
            // 4XX - responses
            println("cje466 Error (4XX) : ${e.response.status.description}")
            ""
        } catch (e: ServerResponseException) {
            // 5XX - responses
            println("cje466 Error (5XX) : ${e.response.status.description}")
            ""
        } catch (e: Exception) {
            println("cje466 Error : ${e.message}")
            ""
        }
    }

    override suspend fun register(user: NewUser): String {
        val httpResponse = httpClient.put<HttpResponse> {
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
        println("cje466: register user: $user httpResponse ${httpResponse}")

        return ""
    }

    override suspend fun logIn(user: LogInUser): LoginResponse {
        return httpClient.post {
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
    }
}


@Serializable
data class LoginResponse(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String
)

fun LoginResponse.isValid(): Boolean {
    return accessToken.isBlank() && refreshToken.isBlank()
}
