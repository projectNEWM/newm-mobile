package io.newm.sharedfeatures.screens.auth.login

import com.varabyte.truthish.assertThat
import kotlin.test.Test

class TextFieldStateTest {
    @Test
    fun `initial state is valid and not dirty`() {
        val state = TextFieldState(validator = { it.length >= 3 })
        assertThat(state.text).isEmpty()
        assertThat(state.isFocusedDirty).isFalse()
        assertThat(state.showErrors()).isFalse()
    }

    @Test
    fun `focus dirty after focus change`() {
        val state = TextFieldState()
        state.onFocusChange(true)
        assertThat(state.isFocusedDirty).isTrue()
        assertThat(state.isFocused).isTrue()
    }

    @Test
    fun `show errors only after focus dirty and invalid`() {
        val state = TextFieldState(validator = { it.isNotEmpty() })
        state.text = ""

        // Not dirty yet
        state.enableShowErrors()
        assertThat(state.showErrors()).isFalse()

        // Become dirty
        state.onFocusChange(true)
        state.onFocusChange(false)
        state.enableShowErrors()

        assertThat(state.showErrors()).isTrue()
    }

    @Test
    fun `isValid reflects validator logic`() {
        val state = TextFieldState(validator = { it == "valid" })
        state.text = "invalid"
        assertThat(state.isValid).isFalse()
        state.text = "valid"
        assertThat(state.isValid).isTrue()
    }
}
