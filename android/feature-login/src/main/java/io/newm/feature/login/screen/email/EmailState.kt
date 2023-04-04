package io.newm.feature.login.screen.email

import io.newm.feature.login.screen.TextFieldState
import io.newm.shared.login.util.LoginFieldValidator

class EmailState : TextFieldState(validator = ::isEmailValid, errorFor = ::emailValidationError)
/**
 * Returns an error to be displayed or null if no error was found
 */
private fun emailValidationError(email: String): String {
    return "Invalid email: $email"
}

private fun isEmailValid(email: String): Boolean {
    return email.isNullOrEmpty()
}
