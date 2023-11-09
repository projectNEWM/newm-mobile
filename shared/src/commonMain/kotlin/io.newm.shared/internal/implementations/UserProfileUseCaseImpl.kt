package io.newm.shared.internal.implementations

import io.newm.shared.public.models.User
import io.newm.shared.internal.repositories.UserRepository
import io.newm.shared.public.usecases.UserProfileUseCase

internal class UserProfileUseCaseImpl(
    private val userRepository: UserRepository
) : UserProfileUseCase {
    override suspend fun getCurrentUser(): User {
        return userRepository.getCurrentUser()
    }
}