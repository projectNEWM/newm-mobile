package io.newm.sharedfeatures.login

import com.google.android.recaptcha.RecaptchaClient
import io.newm.shared.di.dagger.ApplicationScope
import me.tatarka.inject.annotations.Inject

@ApplicationScope
@Inject
class RecaptchaClientProvider {

    private var recaptchaClient: RecaptchaClient? = null

    fun setRecaptchaClient(recaptchaClient: RecaptchaClient) {
        this.recaptchaClient = recaptchaClient
    }

    fun get(): RecaptchaClient = recaptchaClient ?: throw IllegalStateException("RecaptchaClient was not initialized")
}
