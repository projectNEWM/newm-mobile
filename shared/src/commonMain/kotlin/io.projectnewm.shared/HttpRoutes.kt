package io.projectnewm.shared

import io.ktor.http.*

object HttpRoutes {
    private const val BASE_URL = "https://staging-newm-server.herokuapp.com/v1"
    const val REQUEST_CODE = "$BASE_URL/auth/code"
    const val LOGIN = "$BASE_URL/login"
    const val USERS = "$BASE_URL/users"

    val PROTOCOL = URLProtocol.HTTPS
    const val HOST = "staging-newm-server.herokuapp.com"
    const val LOGIN_PATH = "/v1/auth/login"
    const val REGISTER_USER_PATH = "/v1/users"
    const val REQUEST_VERIFICATION_CODE_PATH = "/v1/auth/code"

}