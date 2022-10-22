package io.projectnewm.shared

import io.ktor.http.*

internal object HttpRoutes {
    val PROTOCOL = URLProtocol.HTTPS
    const val HOST = "staging-newm-server.herokuapp.com"
    const val LOGIN_PATH = "v1/auth/login"
    const val REGISTER_USER_PATH = "v1/users"
    const val REQUEST_VERIFICATION_CODE_PATH = "v1/auth/code"
}