package io.newm.shared.internal.implementations

import io.newm.shared.internal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.internal.repositories.LogInRepository
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.ResetPasswordUseCase
import kotlin.coroutines.cancellation.CancellationException

internal class ResetPasswordUseCaseImpl(private var repository: LogInRepository): ResetPasswordUseCase {
    @Throws(KMMException::class, CancellationException::class)
    override suspend fun resetPassword(email: String, code: String, newPassword: String, confirmPassword: String) {
        mapErrorsSuspend {
            repository.resetPassword(email, newPassword, confirmPassword, code)
        }
    }
}
