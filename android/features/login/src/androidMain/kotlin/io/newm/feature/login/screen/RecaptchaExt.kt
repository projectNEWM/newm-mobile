package io.newm.feature.login.screen

import com.google.android.recaptcha.RecaptchaAction

val RecaptchaAction.Companion.LoginGoogle
    get() = custom("login_google")
