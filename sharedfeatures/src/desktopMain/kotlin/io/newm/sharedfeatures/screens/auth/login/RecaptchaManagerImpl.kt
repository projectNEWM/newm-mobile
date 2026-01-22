package io.newm.sharedfeatures.screens.auth.login

import me.tatarka.inject.annotations.Inject

@Inject
actual class RecaptchaManagerImpl : RecaptchaManager {
    actual override suspend fun executeLogin(): Result<String> {
        // No-op for desktop
        return Result.success("mock-token-desktop")
    }
}
