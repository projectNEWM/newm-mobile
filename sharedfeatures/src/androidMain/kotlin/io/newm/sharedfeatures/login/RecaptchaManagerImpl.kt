package io.newm.sharedfeatures.login

import com.google.android.recaptcha.RecaptchaAction
import me.tatarka.inject.annotations.Inject

@Inject
actual class RecaptchaManagerImpl(
    private val recaptchaClientProvider: RecaptchaClientProvider
) : RecaptchaManager {
    actual override suspend fun executeLogin(): Result<String> {
        return try {
            val token = recaptchaClientProvider.get().execute(RecaptchaAction.LOGIN).getOrThrow()
            Result.success(token)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
