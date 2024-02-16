package io.newm.shared.internal.usecases

import io.newm.shared.internal.domainservices.HumanVerificationAction
import io.newm.shared.internal.domainservices.HumanVerificationService
import io.newm.shared.internal.repositories.LogInRepository
import io.newm.shared.internal.usecases.utilities.mapErrorsSuspend
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.ResetPasswordUseCase
import kotlin.coroutines.cancellation.CancellationException

internal class ResetPasswordUseCaseImpl(
    private var repository: LogInRepository,
    private val humanVerificationService: HumanVerificationService
): ResetPasswordUseCase {
    @Throws(KMMException::class, CancellationException::class)
    override suspend fun resetPassword(email: String, code: String, newPassword: String, confirmPassword: String) {
        mapErrorsSuspend {
            val isHuman = humanVerificationService.verify(HumanVerificationAction.CHANGE_PASSWORD)
            repository.resetPassword(email, newPassword, confirmPassword, code, isHuman)
        }
    }
}
