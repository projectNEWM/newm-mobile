package io.newm.sharedfeatures.fakes

import io.newm.sharedfeatures.screens.auth.login.RecaptchaManager

class FakeRecaptchaManager : RecaptchaManager {
    var executeLoginResult: Result<String> = Result.success("fake-token")
    var executeLoginCalled = false

    var executeResult: Result<String> = Result.success("fake-token")
    var executeCalled = false
    var lastExecuteAction: String? = null

    override suspend fun executeLogin(): Result<String> {
        executeLoginCalled = true
        return executeLoginResult
    }

    override suspend fun execute(action: String): Result<String> {
        executeCalled = true
        lastExecuteAction = action
        return executeResult
    }
}
