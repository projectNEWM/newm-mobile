package io.projectnewm.shared.login.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class LoginResponse(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String
)

sealed class LoginError: Throwable() {
    object WrongPassword : LoginError()
    object UserNotFound : LoginError()
    object UnknownError : LoginError()
}

internal fun LoginResponse.isValid(): Boolean {
    return accessToken.isNotBlank() && refreshToken.isNotBlank()
}

sealed class RegisterError: Throwable() {
    object UserAlreadyExists : RegisterError()
    object TwoFactorAuthenticationFailed : RegisterError()
    object UnknownError : RegisterError()
}

sealed class RequestEmailError: Throwable() {
    object Unknown : RequestEmailError()
}

sealed class RequestEmailStatus {
    object Success : RequestEmailStatus()
    object Failure : RequestEmailStatus()
}