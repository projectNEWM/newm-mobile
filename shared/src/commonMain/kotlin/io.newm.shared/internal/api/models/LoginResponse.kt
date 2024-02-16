package io.newm.shared.internal.api.models

import io.newm.shared.public.models.error.KMMException
import kotlinx.serialization.Serializable

//TODO: this should all be internal

@Serializable
internal data class LoginResponse(
    val accessToken: String? = null,
    val refreshToken: String? = null
)

sealed class LoginException(message: String): KMMException(message) {
    data class WrongPassword(override val message: String) : LoginException(message)
    data class UserNotFound(override val message: String) : LoginException(message)
    data class HumanVerificationFailed(override val message: String) : LoginException(message)
}

internal fun LoginResponse.isValid(): Boolean {
    return accessToken?.isNotBlank() == true && refreshToken?.isNotBlank() == true
}

sealed class RegisterException(message: String): KMMException(message) {
    data class UserAlreadyExists(override val message: String) : RegisterException(message)
    data class TwoFactorAuthenticationFailed(override val message: String) : RegisterException(message)
}

sealed class ResetPasswordException(message: String): KMMException(message) {
    data class MissingField(override val message: String) : ResetPasswordException(message)
    data class InvalidAuthCode(override val message: String) : ResetPasswordException(message)
    data class InvalidContent(override val message: String) : ResetPasswordException(message)
    data class EmailNotFound(override val message: String) : ResetPasswordException(message)
}