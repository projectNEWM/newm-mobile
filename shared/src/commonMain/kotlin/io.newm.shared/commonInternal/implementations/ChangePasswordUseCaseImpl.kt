package io.newm.shared.commonInternal.implementations

import io.newm.shared.commonInternal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.commonInternal.repositories.UserRepository
import io.newm.shared.commonPublic.models.error.KMMException
import io.newm.shared.commonPublic.usecases.ChangePasswordUseCase
import kotlin.coroutines.cancellation.CancellationException

internal class ChangePasswordUseCaseImpl(
    private val userRepository: UserRepository,
) : ChangePasswordUseCase {
    @Throws(KMMException::class, CancellationException::class)
    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String,
        confirmPassword: String,
    ) {
        mapErrorsSuspend {
            userRepository.changePassword(oldPassword, newPassword, confirmPassword)
        }
    }
}
