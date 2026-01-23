package io.newm.sharedfeatures.screens.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.newm.shared.login.util.LoginFieldValidator
import newm_mobile.sharedfeatures.generated.resources.Res
import newm_mobile.sharedfeatures.generated.resources.code_verification_error_message
import newm_mobile.sharedfeatures.generated.resources.email_validation_error_message
import newm_mobile.sharedfeatures.generated.resources.password_confirmation_error_message
import newm_mobile.sharedfeatures.generated.resources.password_validation_error_message
import org.jetbrains.compose.resources.StringResource

open class TextFieldState(
    defaultValue: String = "",
    private val validator: (String) -> Boolean = { true },
    private val errorFor: (String) -> StringResource? = { null },
) {
    var text: String by mutableStateOf(defaultValue)

    // was the TextField ever focused
    var isFocusedDirty: Boolean by mutableStateOf(false)
    var isFocused: Boolean by mutableStateOf(false)
    private var displayErrors: Boolean by mutableStateOf(false)

    open val isValid: Boolean
        get() = validator(text)

    fun onFocusChange(focused: Boolean) {
        isFocused = focused
        if (focused) isFocusedDirty = true
    }

    fun enableShowErrors() {
        // only show errors if the text was at least once focused
        if (isFocusedDirty) {
            displayErrors = true
        }
    }

    fun showErrors() = !isValid && displayErrors

    open fun getError(): StringResource? =
        if (showErrors()) {
            errorFor(text)
        } else {
            null
        }
}

class EmailState(
    defaultValue: String = "",
) : TextFieldState(
        defaultValue = defaultValue,
        validator = ::isEmailValid,
        errorFor = ::emailValidationError,
    )

private fun emailValidationError(email: String): StringResource {
    // Note: The original android code passed the email into the string resource.
    // Compose Multiplatform resources don't support format args in the resource object itself
    // easily
    // without the `stringResource` composable.
    // We will return the resource ID and handle formatting in the UI or use a simpler message.
    return Res.string.email_validation_error_message
}

private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

private fun isEmailValid(email: String): Boolean = EMAIL_REGEX.matches(email)

open class PasswordState : TextFieldState(validator = ::isPasswordValid, errorFor = { passwordValidationError() })

class ConfirmPasswordState(
    private val passwordState: PasswordState,
) : PasswordState() {
    override val isValid
        get() = passwordAndConfirmationValid(passwordState.text, text)

    override fun getError(): StringResource? =
        if (showErrors()) {
            passwordConfirmationError()
        } else {
            null
        }
}

private const val MINIMUM_VERIFICATION_CODE_LENGTH = 6

class VerificationCodeState :
    TextFieldState(
        validator = { it.length >= MINIMUM_VERIFICATION_CODE_LENGTH },
        errorFor = {
            // format arg needed: MINIMUM_VERIFICATION_CODE_LENGTH
            Res.string.code_verification_error_message
        },
    )

private fun passwordAndConfirmationValid(
    password: String,
    confirmedPassword: String,
): Boolean = isPasswordValid(password) && password == confirmedPassword

fun isPasswordValid(password: String): Boolean = LoginFieldValidator.isPasswordValid(password)

fun passwordValidationError(): StringResource = Res.string.password_validation_error_message

private fun passwordConfirmationError(): StringResource = Res.string.password_confirmation_error_message
