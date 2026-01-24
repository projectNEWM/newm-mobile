package io.newm.sharedfeatures.screens.auth.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import io.newm.core.ui.text.TextFieldWithLabel
import io.newm.core.ui.text.TextFieldWithLabelDefaults
import newm_mobile.sharedfeatures.generated.resources.Res
import newm_mobile.sharedfeatures.generated.resources.password
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun Password(
    passwordState: PasswordState,
    modifier: Modifier = Modifier,
    label: StringResource = Res.string.password,
    keyboardOptions: KeyboardOptions = TextFieldWithLabelDefaults.KeyboardOptions.PASSWORD,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    TextFieldWithLabel(
        modifier =
            modifier.fillMaxWidth().onFocusChanged { focusState ->
                passwordState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    passwordState.enableShowErrors()
                }
            },
        onValueChange = { passwordState.text = it },
        value = passwordState.text,
        labelResId = label,
        isPassword = true,
        isError = passwordState.showErrors(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        helperText = passwordState.getError()?.let { stringResource(it) },
        singleLine = true,
    )
}
