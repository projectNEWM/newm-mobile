package io.newm.feature.login.screen.email

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import io.newm.core.resources.R
import io.newm.core.ui.text.TextFieldWithLabel
import io.newm.feature.login.screen.TextFieldState

@Composable
fun Email(
    modifier: Modifier = Modifier,
    emailState: TextFieldState,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    TextFieldWithLabel(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                emailState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    emailState.enableShowErrors()
                }
            },
        onValueChange = { emailState.text = it },
        value = emailState.text,
        labelResId = R.string.email,
        isError = emailState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        ),
        helperText = emailState.getError(),
    )
}
