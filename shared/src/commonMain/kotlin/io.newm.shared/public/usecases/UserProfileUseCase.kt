package io.newm.shared.public.usecases

import io.newm.shared.public.models.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface UserProfileUseCase {
    suspend fun getCurrentUser(): User
}

class UserProfileUseCaseProvider(): KoinComponent {
    private val userProfileUseCase: UserProfileUseCase by inject()

    fun get(): UserProfileUseCase {
        return this.userProfileUseCase
    }
}