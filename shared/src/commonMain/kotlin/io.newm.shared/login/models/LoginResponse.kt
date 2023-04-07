package io.newm.shared.login.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String? = null,
    val refreshToken: String? = null
)

sealed class LoginStatus {
    data class Success(val data: LoginResponse) : LoginStatus()
    object WrongPassword : LoginStatus()
    object UserNotFound : LoginStatus()
    object UnknownError : LoginStatus()
}

fun LoginResponse.isValid(): Boolean {
    return accessToken?.isNotBlank() == true && refreshToken?.isNotBlank() == true
}

sealed class RegisterStatus {
    object Success : RegisterStatus()
    object UserAlreadyExists : RegisterStatus()
    object TwoFactorAuthenticationFailed : RegisterStatus()
    object UnknownError : RegisterStatus()
}

sealed class RequestEmailStatus {
    object Success : RequestEmailStatus()
    object Failure : RequestEmailStatus()
}
