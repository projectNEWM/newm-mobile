package io.newm.feature.login.screen.email

import androidx.core.util.PatternsCompat.EMAIL_ADDRESS
import io.newm.feature.login.screen.TextFieldState

class EmailState : TextFieldState(validator = ::isEmailValid, errorFor = ::emailValidationError)
/**
 * Returns an error to be displayed or null if no error was found
 */
private fun emailValidationError(email: String): String {
    return "Invalid email: $email"
}

private fun isEmailValid(email: String): Boolean {
    return EMAIL_ADDRESS.matcher(email).matches()
}
