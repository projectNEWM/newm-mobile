package io.newm.sharedfeatures.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import io.newm.core.theme.inter
import io.newm.core.ui.ToastSideEffect
import io.newm.core.ui.buttons.PrimaryButton
import me.tatarka.inject.annotations.Inject
import newm_mobile.sharedfeatures.generated.resources.Res
import newm_mobile.sharedfeatures.generated.resources.login
import newm_mobile.sharedfeatures.generated.resources.password
import newm_mobile.sharedfeatures.generated.resources.reset_password_forgot_your_password
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginUi(state: LoginScreen.UiState, modifier: Modifier) {
    LoginScreenContent(state = state, modifier = modifier)
}

@Composable
internal fun LoginScreenContent(
    state: LoginScreen.UiState,
    modifier: Modifier = Modifier
) {
    val eventSink = state.eventSink
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    state.errorMessage?.let { msg ->
        ToastSideEffect(stringResource(msg))
    }

    PreLoginArtistBackgroundContentTemplate(
        modifier = modifier,
        isLoading = state.isLoading,
        header = {
            Text(
                text = stringResource(Res.string.reset_password_forgot_your_password),
                fontSize = 16.sp,
                fontFamily = inter,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp)
                    .clickable { eventSink(LoginScreen.UiEvent.ForgotPasswordClick) }
            )
        }
    ) {
        Email(
            modifier = Modifier.focusRequester(focusRequester),
            emailState = state.emailState,
            keyboardOptions = TextFieldWithLabelDefaults.KeyboardOptions.EMAIL.copy(imeAction = ImeAction.Next),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Password(
            label = Res.string.password,
            passwordState = state.passwordState,
            keyboardOptions = TextFieldWithLabelDefaults.KeyboardOptions.PASSWORD.copy(
                imeAction = ImeAction.Go,
            ),
            keyboardActions = KeyboardActions(
                onGo = {
                    keyboardController?.hide()
                    if (state.submitButtonEnabled) {
                        eventSink(LoginScreen.UiEvent.OnLoginClick)
                    }
                }
            ),
        )

        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = stringResource(Res.string.login),
            onClick = { eventSink(LoginScreen.UiEvent.OnLoginClick) },
            enabled = state.submitButtonEnabled,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

class LoginUiFactory @Inject constructor() : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
        return when (screen) {
            LoginScreen -> ui<LoginScreen.UiState> { state, modifier ->
                LoginUi(state, modifier)
            }
            else -> null
        }
    }
}
