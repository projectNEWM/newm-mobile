package io.projectnewm.shared.login.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)

sealed class LoginStatus {
    data class Success(val data: LoginResponse) : LoginStatus()
    object WrongPassword : LoginStatus()
    object UserNotFound : LoginStatus()
    object UnknownError : LoginStatus()
}

fun LoginResponse.isValid(): Boolean {
    return accessToken.isNotBlank() && refreshToken.isNotBlank()
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
