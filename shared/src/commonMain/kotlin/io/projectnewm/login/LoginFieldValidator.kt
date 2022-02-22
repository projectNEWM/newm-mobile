package io.projectnewm.login

class LoginFieldValidator {
    public companion object {
        public fun validate(email: String, password: String): Boolean {
            return isValidEmail(email) && isValidPassword(password)
        }

        private fun isValidEmail(email: CharSequence?): Boolean {
            val emailAddressRegex = Regex(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
            )
            return email?.matches(emailAddressRegex) ?: false
        }

        private fun isValidPassword(password: String?): Boolean {
            val password = password?.let { it } ?: return false
            if (password.isBlank()) { return false }
            return true
        }
    }
}