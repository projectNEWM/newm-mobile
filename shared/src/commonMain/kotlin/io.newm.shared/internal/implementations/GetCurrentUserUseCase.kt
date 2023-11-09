package io.newm.shared.usecases

import io.newm.shared.models.User
import io.newm.shared.repositories.UserRepository

interface GetCurrentUserUseCase {
    suspend fun execute(): User
}

internal class GetCurrentUserUseCaseImpl(private val repository: UserRepository): GetCurrentUserUseCase {
    override suspend fun execute(): User {
        return repository.getCurrentUser()
    }
}