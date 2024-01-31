package io.newm.shared.internal.implementations

import io.newm.shared.internal.repositories.UserRepository
import io.newm.shared.internal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.public.models.User
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.UserDetailsUseCase
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException

internal class UserDetailsUseCaseImpl(
    private val userRepository: UserRepository
) : UserDetailsUseCase {

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun fetchLoggedInUserDetails(): User {
        return mapErrorsSuspend {
            return@mapErrorsSuspend userRepository.fetchLoggedInUserDetails()
        }
    }

    override fun fetchLoggedInUserDetailsFlow(): Flow<User?> {
        return userRepository.fetchUserDetailsFlow()
    }
}