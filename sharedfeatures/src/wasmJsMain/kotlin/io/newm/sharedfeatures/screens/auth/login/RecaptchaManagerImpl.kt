package io.newm.sharedfeatures.screens.auth.login

import me.tatarka.inject.annotations.Inject

@Inject
actual class RecaptchaManagerImpl : RecaptchaManager {
    actual override suspend fun executeLogin(): Result<String> {
        // No-op for web
        return Result.success("mock-token-web")
    }

    actual override suspend fun execute(action: String): Result<String> {
        // No-op for web
        return Result.success("mock-token-web-$action")
    }
}
