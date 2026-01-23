package io.newm.sharedfeatures.screens.auth.login

import me.tatarka.inject.annotations.Inject

@Inject
actual class RecaptchaManagerImpl : RecaptchaManager {
    actual override suspend fun executeLogin(): Result<String> {
        // No-op for desktop
        return Result.success("mock-token-desktop")
    }

    actual override suspend fun execute(action: String): Result<String> {
        // No-op for desktop
        return Result.success("mock-token-desktop-$action")
    }
}
