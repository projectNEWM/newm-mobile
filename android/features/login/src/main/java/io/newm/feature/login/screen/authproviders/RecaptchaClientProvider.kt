package io.newm.feature.login.screen.authproviders

import com.google.android.recaptcha.RecaptchaClient

class RecaptchaClientProvider {

    private var recaptchaClient: RecaptchaClient? = null

    fun setRecaptchaClient(recaptchaClient: RecaptchaClient) {
        this.recaptchaClient = recaptchaClient
    }

    fun get(): RecaptchaClient = recaptchaClient ?: throw IllegalStateException("RecaptchaClient was not initialized")
}

