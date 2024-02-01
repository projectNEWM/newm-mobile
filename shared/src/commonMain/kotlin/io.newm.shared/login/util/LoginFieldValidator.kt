package io.newm.shared.login.util

object LoginFieldValidator {

    fun validate(email: String, password: String): Boolean {
        return isEmailValid(email) && isPasswordValid(password)
    }

    fun isPasswordValid(password: String): Boolean {
        return password.isNotBlank() && password.length > 3
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