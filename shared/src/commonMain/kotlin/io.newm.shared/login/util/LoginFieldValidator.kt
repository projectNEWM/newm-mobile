package io.newm.shared.login.util

object LoginFieldValidator {

    fun validate(email: String, password: String): Boolean {
        return isValidEmail(email) && isValidPassword(password)
    }

    fun isEmailValid(email: String): Boolean = isValidEmail(email)

    fun isPasswordValid(password: String): Boolean = isValidPassword(password)

    private fun isValidEmail(email: String): Boolean {
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

    private fun isValidPassword(password: String): Boolean {
        return password.isNotBlank() && password.length > 3
    }
}