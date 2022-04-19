package io.projectnewm.shared.login

import io.ktor.client.*
import io.ktor.client.request.*

//TODO: Find a better home to base URL
const val BASE_URL = "https://staging-newm-server.herokuapp.com"

interface LogInService {
    suspend fun requestEmailConfirmationCode(email: String): String
    suspend fun register(user: NewUser): String
    suspend fun logIn(user: LogInUser): String
}

//TODO: Handle Error Cases
class LogInServiceImpl(
    private val httpClient: HttpClient
) : LogInService {
    override suspend fun requestEmailConfirmationCode(email: String): String {
        return httpClient.get {
            url("$BASE_URL/auth/code?email=$email")
        }
    }

    override suspend fun register(user: NewUser): String {
        return httpClient.put(body = user) {
            url("$BASE_URL/v1/users")
        }
    }

    override suspend fun logIn(user: LogInUser): String {
        return httpClient.post(body = user) {
            url("$BASE_URL/login")
        }
    }
}
