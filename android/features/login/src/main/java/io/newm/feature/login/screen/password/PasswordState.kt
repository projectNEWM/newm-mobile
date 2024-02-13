package io.newm.feature.login.screen.password

import io.newm.feature.login.screen.TextFieldState
import io.newm.shared.login.util.LoginFieldValidator

class PasswordState :
    TextFieldState(validator = ::isPasswordValid, errorFor = ::passwordValidationError)

class ConfirmPasswordState(private val passwordState: PasswordState) : TextFieldState() {
    override val isValid
        get() = passwordAndConfirmationValid(passwordState.text, text)

    override fun getError(): String? {
        return if (showErrors()) {
            passwordConfirmationError()
        } else {
            null
        }
    }
}

private const val MINIMUM_VERIFICATION_CODE_LENGTH = 6

class VerificationCodeState :  TextFieldState(
    validator = { it.length >= MINIMUM_VERIFICATION_CODE_LENGTH },
    errorFor = { "Verification code must be at least $MINIMUM_VERIFICATION_CODE_LENGTH characters" },
)

private fun passwordAndConfirmationValid(password: String, confirmedPassword: String): Boolean {
    return isPasswordValid(password) && password == confirmedPassword
}

private fun isPasswordValid(password: String): Boolean {
    return LoginFieldValidator.isPasswordValid(password)
}

private fun passwordValidationError(password: String): String {
    return "Password must contain at least 8 characters, 1 uppercase letter, 1 lowercase letter, and 1 number."
}

private fun passwordConfirmationError(): String {
    return "Passwords don't match"
}
