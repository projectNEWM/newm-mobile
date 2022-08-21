package io.projectnewm.shared.login.repository

import io.projectnewm.shared.login.models.*
import io.projectnewm.shared.login.service.LogInService

//TODO: Handle Error Cases
internal class LogInRepository(
    private val service: LogInService
) {
    suspend fun requestEmailConfirmationCode(email: String): RequestEmailStatus {
        return service.requestEmailConfirmationCode(email)
    }

    suspend fun registerUser(user: NewUser): RegisterStatus {
        return service.register(user)
    }

    suspend fun logIn(email: String, password: String): LoginStatus {
        return service.logIn(LogInUser(email = email, password = password))
    }

    suspend fun registerUser(
        firstName: String,
        lastName: String,
        pictureUrl: String,
        email: String,
        newPassword: String,
        confirmPassword: String,
        authCode: String
    ): RegisterStatus {
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