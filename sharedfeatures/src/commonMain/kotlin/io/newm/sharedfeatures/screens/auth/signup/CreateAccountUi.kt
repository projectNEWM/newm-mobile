package io.newm.sharedfeatures.screens.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import io.newm.core.ui.LoadingScreen
import io.newm.core.ui.ToastSideEffect
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.sharedfeatures.screens.auth.login.Email
import io.newm.sharedfeatures.screens.auth.login.Password
import io.newm.sharedfeatures.screens.auth.login.PreLoginArtistBackgroundContentTemplate
import io.newm.sharedfeatures.screens.auth.login.TextFieldState
import io.newm.sharedfeatures.screens.auth.login.TextFieldWithLabel
import io.newm.sharedfeatures.screens.auth.login.TextFieldWithLabelDefaults
import io.newm.sharedfeatures.screens.auth.login.UiMessage
import io.newm.sharedfeatures.screens.auth.signup.CreateAccountUiState.EmailAndPasswordUiState
import io.newm.sharedfeatures.screens.auth.signup.CreateAccountUiState.EmailVerificationUiState
import me.tatarka.inject.annotations.Inject
import newm_mobile.sharedfeatures.generated.resources.Res
import newm_mobile.sharedfeatures.generated.resources.confirm_password
import newm_mobile.sharedfeatures.generated.resources.login_check_your_email
import newm_mobile.sharedfeatures.generated.resources.login_continue
import newm_mobile.sharedfeatures.generated.resources.login_enter_verification_code
import newm_mobile.sharedfeatures.generated.resources.login_enter_verification_code_below
import newm_mobile.sharedfeatures.generated.resources.login_receive_email
import newm_mobile.sharedfeatures.generated.resources.next
import newm_mobile.sharedfeatures.generated.resources.password
import org.jetbrains.compose.resources.stringResource

@Composable
fun CreateAccountUi(
    state: CreateAccountUiState,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is EmailAndPasswordUiState -> {
            EmailAndPasswordUi(modifier, state)
        }

        is EmailVerificationUiState -> {
            EmailVerificationUi(modifier, state)
        }

        CreateAccountUiState.Loading -> {
            LoadingScreen()
        }
    }
}

@Composable
internal fun EmailAndPasswordUi(
    modifier: Modifier,
    state: EmailAndPasswordUiState,
) {
    val onEvent = state.eventSink
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    state.errorMessage?.let { msg ->
        val text =
            when (msg) {
                is UiMessage.Resource -> stringResource(msg.resId)
                is UiMessage.Text -> msg.text
            }
        ToastSideEffect(text)
    }

    PreLoginArtistBackgroundContentTemplate(modifier = modifier) {
        Email(
            modifier = Modifier.focusRequester(focusRequester),
            emailState = state.emailState,
            keyboardOptions =
                TextFieldWithLabelDefaults.KeyboardOptions.EMAIL.copy(imeAction = ImeAction.Next),
        )

        Password(
            label = Res.string.password,
            passwordState = state.passwordState,
            keyboardOptions =
                TextFieldWithLabelDefaults.KeyboardOptions.PASSWORD.copy(imeAction = ImeAction.Next),
        )

        Password(
            label = Res.string.confirm_password,
            passwordState = state.passwordConfirmationState,
            keyboardOptions =
                TextFieldWithLabelDefaults.KeyboardOptions.PASSWORD.copy(imeAction = ImeAction.Go),
            keyboardActions =
                KeyboardActions(
                    onGo = {
                        keyboardController?.hide()
                        if (state.submitButtonEnabled) {
                            onEvent(SignupFormUiEvent.Next)
                        }
                    },
                ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.next),
            onClick = { onEvent(SignupFormUiEvent.Next) },
            enabled = state.submitButtonEnabled,
        )
    }
}

@Composable
internal fun EmailVerificationUi(
    modifier: Modifier = Modifier,
    state: EmailVerificationUiState,
) {
    val onEvent = state.eventSink

    EmailVerificationContent(
        modifier = modifier,
        verificationCode = state.verificationCode,
        errorMessage = state.errorMessage,
        nextButtonEnabled = state.nextButtonEnabled,
        onNextClicked = { onEvent(EmailVerificationUiEvent.Next) },
    )
}

@Composable
fun EmailVerificationContent(
    verificationCode: TextFieldState,
    errorMessage: UiMessage?,
    nextButtonEnabled: Boolean,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    errorMessage?.let { msg ->
        val text =
            when (msg) {
                is UiMessage.Resource -> stringResource(msg.resId)
                is UiMessage.Text -> msg.text
            }
        ToastSideEffect(text)
    }

    Box(
        modifier =
            modifier.fillMaxHeight().fillMaxWidth().background(MaterialTheme.colors.background),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(Res.string.login_check_your_email),
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h1,
            )
            Text(
                text = stringResource(Res.string.login_enter_verification_code_below),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
            )
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = stringResource(Res.string.login_receive_email),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.caption,
            )
            Spacer(modifier = Modifier.height(36.dp))

            TextFieldWithLabel(
                labelResId = Res.string.login_enter_verification_code,
                value = verificationCode.text,
                onValueChange = { verificationCode.text = it },
                keyboardOptions =
                    TextFieldWithLabelDefaults.KeyboardOptions.Digits.copy(
                        imeAction = ImeAction.Go,
                    ),
                keyboardActions =
                    KeyboardActions(
                        onGo = {
                            if (nextButtonEnabled) {
                                onNextClicked()
                            }
                        },
                    ),
            )

            Spacer(modifier = Modifier.height(16.dp))
            PrimaryButton(
                text = stringResource(Res.string.login_continue),
                enabled = nextButtonEnabled,
                onClick = { onNextClicked() },
            )
        }
    }
}

class CreateAccountUiFactory
    @Inject
    constructor() : Ui.Factory {
        override fun create(
            screen: Screen,
            context: CircuitContext,
        ): Ui<*>? =
            when (screen) {
                CreateAccountScreen -> {
                    ui<CreateAccountUiState> { state, modifier -> CreateAccountUi(state, modifier) }
                }

                else -> {
                    null
                }
            }
    }
