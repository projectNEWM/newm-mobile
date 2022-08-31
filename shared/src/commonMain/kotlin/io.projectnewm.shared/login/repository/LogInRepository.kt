package io.projectnewm.shared.login.repository

import io.projectnewm.shared.login.models.*
import io.projectnewm.shared.login.service.LogInService
//import io.projectnewm.shared.users.UserDAO
import kotlin.coroutines.cancellation.CancellationException

//TODO: Handle Error Cases
internal class LogInRepository(
    private val service: LogInService,
//    private val dao: UserDAO
) {
    @Throws(RequestEmailError::class, CancellationException::class)
    suspend fun requestEmailConfirmationCode(email: String) {
        service.requestEmailConfirmationCode(email)
    }

    @Throws(RegisterError::class, CancellationException::class)
    suspend fun registerUser(user: NewUser) {
        service.register(user)
    }

    @Throws(LoginError::class, CancellationException::class)
    suspend fun logIn(email: String, password: String) {
        service.logIn(LogInUser(email = email, password = password))
    }

    @Throws(RegisterError::class, CancellationException::class)
    suspend fun registerUser(
        firstName: String,
        lastName: String,
        pictureUrl: String,
        email: String,
        newPassword: String,
        confirmPassword: String,
        authCode: String
    ) {
        service.register(
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

//    val currentUser =  {
//        UserDAO.get()
//    }
}