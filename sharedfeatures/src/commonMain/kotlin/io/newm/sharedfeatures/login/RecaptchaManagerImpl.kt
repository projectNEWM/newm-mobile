package io.newm.sharedfeatures.login

import me.tatarka.inject.annotations.Inject

@Inject
expect class RecaptchaManagerImpl : RecaptchaManager {
    override suspend fun executeLogin(): Result<String>
}
