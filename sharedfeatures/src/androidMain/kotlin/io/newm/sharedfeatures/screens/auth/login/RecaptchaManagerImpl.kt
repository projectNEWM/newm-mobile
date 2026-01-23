package io.newm.sharedfeatures.screens.auth.login

import com.google.android.recaptcha.RecaptchaAction
import me.tatarka.inject.annotations.Inject

@Inject
actual class RecaptchaManagerImpl(
    private val recaptchaClientProvider: RecaptchaClientProvider,
) : RecaptchaManager {
    actual override suspend fun executeLogin(): Result<String> =
        try {
            val token = recaptchaClientProvider.get().execute(RecaptchaAction.LOGIN).getOrThrow()
            Result.success(token)
        } catch (e: Exception) {
            Result.failure(e)
        }

    actual override suspend fun execute(action: String): Result<String> =
        try {
            val token =
                recaptchaClientProvider.get().execute(RecaptchaAction.custom(action)).getOrThrow()
            Result.success(token)
        } catch (e: Exception) {
            Result.failure(e)
        }
}
