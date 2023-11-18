package io.newm.shared.internal.implementations

import io.newm.shared.public.models.User
import io.newm.shared.internal.repositories.UserRepository
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.UserDetailsUseCase
import kotlin.coroutines.cancellation.CancellationException

internal class UserDetailsUseCaseImpl(
    private val userRepository: UserRepository
) : UserDetailsUseCase {

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun fetchLoggedInUserDetails(): User {
        return userRepository.fetchLoggedInUserDetails()
    }
}