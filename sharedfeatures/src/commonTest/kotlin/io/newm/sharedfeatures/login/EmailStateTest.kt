package io.newm.sharedfeatures.login

import com.varabyte.truthish.assertThat
import kotlin.test.Test

class EmailStateTest {
    @Test
    fun `validate emails correctly`() {
        val state = EmailState()

        val validEmails = listOf("test@newm.io", "user.name+tag@gmail.com", "123@abc.co")
        val invalidEmails = listOf("test", "test@", "@test.com")

        validEmails.forEach { email ->
            state.text = email
            assertThat(state.isValid).isTrue()
        }

        invalidEmails.forEach { email ->
            state.text = email
            assertThat(state.isValid).isFalse()
        }
    }
}
