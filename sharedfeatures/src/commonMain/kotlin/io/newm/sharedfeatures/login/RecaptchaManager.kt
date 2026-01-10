package io.newm.sharedfeatures.login

interface RecaptchaManager {
    suspend fun executeLogin(): Result<String>
}
