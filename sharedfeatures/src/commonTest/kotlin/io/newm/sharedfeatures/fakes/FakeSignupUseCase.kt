package io.newm.sharedfeatures.fakes

import io.newm.shared.commonPublic.usecases.SignupUseCase

class FakeSignupUseCase : SignupUseCase {
    var requestEmailConfirmationCodeCalled = false
    var registerUserCalled = false

    var requestEmailConfirmationCodeResult: Result<Unit> = Result.success(Unit)
    var registerUserResult: Result<Unit> = Result.success(Unit)

    override suspend fun requestEmailConfirmationCode(
        email: String,
        humanVerificationCode: String,
        mustExists: Boolean,
    ) {
        requestEmailConfirmationCodeCalled = true
        requestEmailConfirmationCodeResult.getOrThrow()
    }

    override suspend fun registerUser(
        email: String,
        verificationCode: String,
        password: String,
        passwordConfirmation: String,
        humanVerificationCode: String,
    ) {
        registerUserCalled = true
        registerUserResult.getOrThrow()
    }
}
