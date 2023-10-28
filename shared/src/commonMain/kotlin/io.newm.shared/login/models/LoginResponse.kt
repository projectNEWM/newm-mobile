package io.newm.shared.login.models

import io.newm.shared.login.repository.KMMException
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String? = null,
    val refreshToken: String? = null
)

sealed class LoginException(message: String): KMMException(message) {
    data class WrongPassword(override val message: String) : LoginException(message)
    data class UserNotFound(override val message: String) : LoginException(message)
}

fun LoginResponse.isValid(): Boolean {
    return accessToken?.isNotBlank() == true && refreshToken?.isNotBlank() == true
}

sealed class RegisterException(message: String): KMMException(message) {
    data class UserAlreadyExists(override val message: String) : RegisterException(message)
    data class TwoFactorAuthenticationFailed(override val message: String) : RegisterException(message)
}