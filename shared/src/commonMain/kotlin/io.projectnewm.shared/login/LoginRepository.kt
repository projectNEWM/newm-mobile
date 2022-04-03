package io.projectnewm.shared.login

//TODO: Handle Error Cases
class LoginRepository(
    private val service: LoginService
) {
    suspend fun registerUser(email: String): String {
        return service.requestSignUpAccess(email)
    }

    suspend fun registerUser(user: NewUser): String {
        return service.register(user)
    }

    suspend fun login(email: String, password: String): String {
        return service.login(LoginUser(email = email, password = password))
    }

    suspend fun registerUser(
        firstName: String,
        lastName: String,
        pictureUrl: String,
        email: String,
        newPassword: String,
        confirmPassword: String,
        authCode: String
    ): String {
        return service.register(
            NewUser(
                firstName,
                lastName,
                pictureUrl,
                email,
                newPassword,
                confirmPassword,
                authCode
            )
        )
    }
}