package io.newm.sharedfeatures.fakes

import io.newm.shared.commonPublic.usecases.ResetPasswordUseCase

class FakeResetPasswordUseCase : ResetPasswordUseCase {
    var resetPasswordCalled = false
    var resetPasswordResult: Result<Unit> = Result.success(Unit)

    override suspend fun resetPassword(
        email: String,
        code: String,
        newPassword: String,
        confirmPassword: String,
        humanVerificationCode: String,
    ) {
        resetPasswordCalled = true
        resetPasswordResult.getOrThrow()
    }
}
