package io.projectnewm.shared.login

import io.ktor.client.*
import io.ktor.client.request.*

interface LoginService {
    suspend fun requestSignUpAccess(email: String): String
    suspend fun register(user: NewUser): String
    suspend fun login(user: LoginUser): String

    companion object {
        const val BASE_URL = "https://staging-newm-server.herokuapp.com"
    }
}

//TODO: Handle Error Cases
class LoginServiceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String = LoginService.BASE_URL,
) : LoginService {
    override suspend fun requestSignUpAccess(email: String): String {
        return httpClient.get {
            url("$baseUrl/auth/code?email=$email")
        }
    }

    override suspend fun register(user: NewUser): String {
        return httpClient.put(body = user) {
            url("$baseUrl/v1/users")
        }
    }

    override suspend fun login(user: LoginUser): String {
        return httpClient.post(body = user) {
            url("$baseUrl/login")
        }
    }
}
