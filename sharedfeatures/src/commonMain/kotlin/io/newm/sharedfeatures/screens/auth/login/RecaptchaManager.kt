package io.newm.sharedfeatures.screens.auth.login

interface RecaptchaManager {
    suspend fun executeLogin(): Result<String>
}
