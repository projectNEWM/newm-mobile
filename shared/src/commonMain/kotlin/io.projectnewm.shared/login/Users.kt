package io.projectnewm.shared.login

data class LoginUser(val email: String, val password: String)

data class NewUser(
    val firstName: String,
    val lastName: String,
    val pictureUrl: String,
    val email: String,
    val newPassword: String,
    val confirmPassword: String,
    val authCode: String
)
