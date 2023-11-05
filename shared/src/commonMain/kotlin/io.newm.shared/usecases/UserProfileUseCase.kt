package io.newm.shared.usecases

import io.newm.shared.models.User
import io.newm.shared.repositories.UserRepository

interface UserProfileUseCase {
    suspend fun getCurrentUser(): User
}

internal class UserProfileUseCaseImpl(
    private val userRepository: UserRepository
) : UserProfileUseCase {
    override suspend fun getCurrentUser(): User {
        return userRepository.getCurrentUser()
    }
}