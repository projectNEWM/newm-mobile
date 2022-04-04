package io.projectnewm.shared.login

import io.ktor.client.*
import io.ktor.client.request.*

interface LogInService {
    suspend fun requestEmailConfirmationCode(email: String): String
    suspend fun register(user: NewUser): String
    suspend fun logIn(user: LogInUser): String

    companion object {
        //TODO: Find a better home to base URL
        const val BASE_URL = "https://staging-newm-server.herokuapp.com"
    }
}

//TODO: Handle Error Cases
class LogInServiceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String = LogInService.BASE_URL,
) : LogInService {
    override suspend fun requestEmailConfirmationCode(email: String): String {
        return httpClient.get {
            url("$baseUrl/auth/code?email=$email")
        }
    }

    override suspend fun register(user: NewUser): String {
        return httpClient.put(body = user) {
            url("$baseUrl/v1/users")
        }
    }

    override suspend fun logIn(user: LogInUser): String {
        return httpClient.post(body = user) {
            url("$baseUrl/login")
        }
    }
}
