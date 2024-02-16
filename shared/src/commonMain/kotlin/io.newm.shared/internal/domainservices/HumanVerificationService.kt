package io.newm.shared.internal.domainservices

interface HumanVerificationService {
    suspend fun setUp()
    suspend fun verify(action: HumanVerificationAction): String
}

enum class HumanVerificationAction {
    SIGN_UP,
    CHANGE_PASSWORD,
    AUTH_CODE,
    LOGIN_EMAIL,
    LOGIN_GOOGLE,
    LOGIN_APPLE
}
