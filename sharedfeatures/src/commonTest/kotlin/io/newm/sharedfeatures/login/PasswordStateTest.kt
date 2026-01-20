package io.newm.sharedfeatures.login

import com.varabyte.truthish.assertThat
import kotlin.test.Test

class PasswordStateTest {
    @Test
    fun `validate passwords correctly`() {
        val state = PasswordState()

        val validPasswords = listOf("Password123!", "Abcdefg1", "1234567aA")
        val invalidPasswords =
            listOf(
                "short1A", // too short
                "alllowercase1", // no uppercase
                "ALLUPPERCASE1", // no lowercase
                "NoNumbers", // no numbers
                "12345678", // no letters
            )

        validPasswords.forEach { password ->
            state.text = password
            assertThat(state.isValid).isTrue()
        }

        invalidPasswords.forEach { password ->
            state.text = password
            assertThat(state.isValid).isFalse()
        }
    }

    @Test
    fun `confirm password state works`() {
        val passwordState = PasswordState()
        val confirmState = ConfirmPasswordState(passwordState)

        passwordState.text = "Password123!"
        confirmState.text = "wrong"
        assertThat(confirmState.isValid).isFalse()

        confirmState.text = "Password123!"
        // Both must be valid and match
        if (passwordState.isValid) {
            assertThat(confirmState.isValid).isTrue()
        }
    }
}
