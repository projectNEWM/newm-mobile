package io.newm.shared.commonInternal.implementations

import io.newm.shared.commonInternal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.commonInternal.repositories.LogInRepository
import io.newm.shared.commonPublic.models.error.KMMException
import io.newm.shared.commonPublic.usecases.ResetPasswordUseCase
import me.tatarka.inject.annotations.Inject
import kotlin.coroutines.cancellation.CancellationException

@Inject
class ResetPasswordUseCaseImpl(
    private var repository: LogInRepository,
) : ResetPasswordUseCase {
    @Throws(KMMException::class, CancellationException::class)
    override suspend fun resetPassword(
        email: String,
        code: String,
        newPassword: String,
        confirmPassword: String,
        humanVerificationCode: String,
    ) {
        mapErrorsSuspend {
            repository.resetPassword(
                email,
                newPassword,
                confirmPassword,
                code,
                humanVerificationCode,
            )
        }
    }
}
