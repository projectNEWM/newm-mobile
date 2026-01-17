package io.newm.sharedfeatures.fakes

import io.newm.shared.commonPublic.usecases.LoginUseCase

class FakeLoginUseCase : LoginUseCase {
    var logInCalled = false
    var lastEmail = ""
    var lastPassword = ""
    var lastHumanVerificationCode = ""
    var logInResult: Result<Unit> = Result.success(Unit)

    override suspend fun logIn(email: String, password: String, humanVerificationCode: String) {
        logInCalled = true
        lastEmail = email
        lastPassword = password
        lastHumanVerificationCode = humanVerificationCode
        logInResult.getOrThrow()
    }

    override suspend fun logInWithGoogle(idToken: String, humanVerificationCode: String) {
        TODO("Not yet implemented")
    }

    override suspend fun logInWithFacebook(accessToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun logInWithLinkedIn(accessToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun logInWithApple(idToken: String, humanVerificationCode: String) {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }
}
