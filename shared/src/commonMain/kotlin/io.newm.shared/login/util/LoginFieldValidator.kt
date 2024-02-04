package io.newm.shared.login.util

object LoginFieldValidator {

    /**
     * Password regex, it must contain the following:
     * 8 characters, 1 uppercase letter, 1 lowercase letter and 1 number.
     */
    private val PASSWORD_REGEX = Regex(pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}\$")

    fun validate(email: String, password: String): Boolean {
        return isEmailValid(email) && isPasswordValid(password)
    }

    fun isPasswordValid(password: String): Boolean {
        return PASSWORD_REGEX.matches(password)
    }

    fun isEmailValid(email: String): Boolean {
        val emailAddressRegex = Regex(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
        return email.matches(emailAddressRegex)
    }
}
