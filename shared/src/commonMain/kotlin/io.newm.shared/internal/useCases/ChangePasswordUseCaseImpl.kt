package io.newm.shared.internal.useCases

import io.newm.shared.internal.repositories.UserRepository
import io.newm.shared.internal.useCases.utilities.mapErrorsSuspend
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.ChangePasswordUseCase
import kotlin.coroutines.cancellation.CancellationException

internal class ChangePasswordUseCaseImpl(
    private val userRepository: UserRepository
) : ChangePasswordUseCase {
    @Throws(KMMException::class, CancellationException::class)
    override suspend fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String) {
        mapErrorsSuspend {
            userRepository.changePassword(oldPassword, newPassword, confirmPassword)
        }
    }
}