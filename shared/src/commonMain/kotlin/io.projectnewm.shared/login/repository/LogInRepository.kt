package io.projectnewm.shared.login.repository

import io.projectnewm.shared.login.models.LogInUser
import io.projectnewm.shared.login.models.NewUser
import io.projectnewm.shared.login.service.LogInService
import io.projectnewm.shared.login.service.isValid

//TODO: Handle Error Cases
internal class LogInRepository(
    private val service: LogInService
) {
    suspend fun requestEmailConfirmationCode(email: String): String {
        return service.requestEmailConfirmationCode(email)
    }

    suspend fun registerUser(user: NewUser): String {
        return service.register(user)
    }

    suspend fun logIn(email: String, password: String): Boolean {
        val response = service.logIn(LogInUser(email = email, password = password))
        return response != null && response.isValid()
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