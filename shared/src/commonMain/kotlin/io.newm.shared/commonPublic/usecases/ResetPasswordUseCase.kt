package io.newm.shared.commonPublic.usecases

import io.newm.shared.commonPublic.models.error.KMMException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

interface ResetPasswordUseCase {
    @Throws(KMMException::class, CancellationException::class)
    suspend fun resetPassword(
        email: String,
        code: String,
        newPassword: String,
        confirmPassword: String,
        humanVerificationCode: String,
    )
}

class ResetPasswordUseCaseProvider : KoinComponent {
    private val resetPasswordUseCase: ResetPasswordUseCase by inject()

    fun get(): ResetPasswordUseCase = this.resetPasswordUseCase
}
