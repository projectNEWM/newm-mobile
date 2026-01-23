package io.newm.sharedfeatures.screens.auth.login

import me.tatarka.inject.annotations.Inject

@Inject
expect class RecaptchaManagerImpl : RecaptchaManager {
    override suspend fun executeLogin(): Result<String>

    override suspend fun execute(action: String): Result<String>
}
