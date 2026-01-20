package io.newm.shared.commonInternal.implementations

import io.newm.shared.commonInternal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.commonInternal.repositories.UserRepository
import io.newm.shared.commonPublic.models.User
import io.newm.shared.commonPublic.models.error.KMMException
import io.newm.shared.commonPublic.usecases.UserDetailsUseCase
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException

internal class UserDetailsUseCaseImpl(
    private val userRepository: UserRepository,
) : UserDetailsUseCase {
    @Throws(KMMException::class, CancellationException::class)
    override suspend fun fetchLoggedInUserDetails(): User {
        return mapErrorsSuspend {
            return@mapErrorsSuspend userRepository.fetchLoggedInUserDetails()
        }
    }

    override fun fetchLoggedInUserDetailsFlow(): Flow<User?> = userRepository.fetchUserDetailsFlow()

    override suspend fun updateUserDetails(user: User) =
        mapErrorsSuspend {
            userRepository.updateUserDetails(user)
        }
}
