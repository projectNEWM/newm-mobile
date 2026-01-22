package io.newm.sharedfeatures.fakes

import io.newm.sharedfeatures.screens.auth.login.RecaptchaManager
import kotlinx.coroutines.yield

class FakeRecaptchaManager : RecaptchaManager {
    var executeLoginResult: Result<String> = Result.success("fake-token")
    var executeLoginCalled = false

    override suspend fun executeLogin(): Result<String> {
        yield()
        executeLoginCalled = true
        return executeLoginResult
    }
}
