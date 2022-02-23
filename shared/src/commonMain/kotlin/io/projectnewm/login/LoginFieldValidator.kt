package io.projectnewm.login

object LoginFieldValidator {
        public fun validate(email: String, password: String): Boolean {
            return isValidEmail(email) && isValidPassword(password)
        }

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
            return password.isBlank() == false
        }
}