package io.newm.shared.login.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String? = null,
    val refreshToken: String? = null
)

sealed class LoginException: Throwable() {
    object WrongPassword : LoginException()
    object UserNotFound : LoginException()
    object UnknownError : LoginException()
}

fun LoginResponse.isValid(): Boolean {
    return accessToken?.isNotBlank() == true && refreshToken?.isNotBlank() == true
}

enum class RegisterError {
    UserAlreadyExists,
    TwoFactorAuthenticationFailed,
    UnknownError
}