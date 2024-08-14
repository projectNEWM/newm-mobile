package io.newm.shared.internal.api.utils

import io.ktor.client.request.HttpRequestBuilder
import shared.getPlatformName

fun HttpRequestBuilder.addHumanVerificationCodeToHeader(humanVerificationCode: String) {
    this.headers.append("g-recaptcha-token", humanVerificationCode)
    this.headers.append("g-recaptcha-platform", getPlatformName())
}
