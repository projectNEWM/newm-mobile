package io.newm.sharedfeatures.screens.auth.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import newm_mobile.sharedfeatures.generated.resources.Res
import newm_mobile.sharedfeatures.generated.resources.email
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun Email(
    emailState: EmailState,
    modifier: Modifier = Modifier,
    label: StringResource = Res.string.email,
    keyboardOptions: KeyboardOptions = TextFieldWithLabelDefaults.KeyboardOptions.EMAIL,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    TextFieldWithLabel(
        modifier =
            modifier.fillMaxWidth().onFocusChanged { focusState ->
                emailState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    emailState.enableShowErrors()
                }
            },
        onValueChange = { emailState.text = it },
        value = emailState.text,
        labelResId = label,
        isError = emailState.showErrors(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        helperText = emailState.getError()?.let { stringResource(it, emailState.text) },
        singleLine = true,
    )
}
