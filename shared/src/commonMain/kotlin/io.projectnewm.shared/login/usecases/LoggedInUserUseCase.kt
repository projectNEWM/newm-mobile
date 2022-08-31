package io.projectnewm.shared.login.usecases

import io.projectnewm.shared.login.repository.LogInRepository
import io.projectnewm.shared.models.User
import io.projectnewm.shared.util.CommonFlow
import io.projectnewm.shared.util.DataState
import io.projectnewm.shared.util.asCommonFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

interface LoggedInUserUseCase {
    val loggedInUser: CommonFlow<User>
}

internal class LoggedInUserUseCaseImpl(private val repository: LogInRepository) : LoggedInUserUseCase {
    override val loggedInUser: CommonFlow<User> = flow {
        delay(500)
        emit(User(userName = "user", id = "id"))
    }.asCommonFlow()
}