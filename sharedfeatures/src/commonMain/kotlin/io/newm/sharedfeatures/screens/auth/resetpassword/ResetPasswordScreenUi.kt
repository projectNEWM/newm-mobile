package io.newm.sharedfeatures.screens.auth.resetpassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import io.newm.core.ui.ToastSideEffect
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens
import io.newm.sharedfeatures.screens.auth.login.Email
import io.newm.sharedfeatures.screens.auth.login.Password
import io.newm.sharedfeatures.screens.auth.login.TextFieldWithLabelDefaults
import io.newm.sharedfeatures.screens.auth.login.UiMessage
import io.newm.sharedfeatures.screens.auth.signup.EmailVerificationContent
import me.tatarka.inject.annotations.Inject
import newm_mobile.sharedfeatures.generated.resources.Res
import newm_mobile.sharedfeatures.generated.resources.confirm
import newm_mobile.sharedfeatures.generated.resources.reset_password_confirm_new_password
import newm_mobile.sharedfeatures.generated.resources.reset_password_enter_email
import newm_mobile.sharedfeatures.generated.resources.reset_password_enter_email_continue
import newm_mobile.sharedfeatures.generated.resources.reset_password_enter_new_password
import newm_mobile.sharedfeatures.generated.resources.reset_password_forgot_your_password
import newm_mobile.sharedfeatures.generated.resources.reset_password_new_password
import org.jetbrains.compose.resources.stringResource

@Composable
fun ResetPasswordScreenUi(
    state: ResetPasswordScreenUiState,
    eventLogger: NewmAppEventLogger,
    modifier: Modifier = Modifier,
) {
    ResetPasswordScreenContent(modifier = modifier, state = state, eventLogger = eventLogger)
}

@Composable
internal fun ResetPasswordScreenContent(
    state: ResetPasswordScreenUiState,
    modifier: Modifier = Modifier,
    eventLogger: NewmAppEventLogger,
) {
    state.errorMessage?.let { msg ->
        val text =
            when (msg) {
                is UiMessage.Resource -> stringResource(msg.resId)
                is UiMessage.Text -> msg.text
            }
        ToastSideEffect(text)
    }

    Scaffold(modifier = modifier.fillMaxSize()) { padding ->
        when (state) {
            is ResetPasswordScreenUiState.EnterEmail -> {
                EnterEmailContent(state, modifier = Modifier.padding(padding), eventLogger)
            }

            is ResetPasswordScreenUiState.EnterVerificationCode -> {
                EnterCodeContent(state, modifier = Modifier.padding(padding), eventLogger)
            }

            is ResetPasswordScreenUiState.EnterNewPassword -> {
                SetNewPasswordContent(state, modifier = Modifier.padding(padding), eventLogger)
            }
        }
        if (state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun EnterEmailContent(
    state: ResetPasswordScreenUiState.EnterEmail,
    modifier: Modifier = Modifier,
    eventLogger: NewmAppEventLogger,
) {
    val eventSink = state.eventSink
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) { eventLogger.logPageLoad(AppScreens.ResetPasswordEnterEmailScreen.name) }
    Column(modifier = modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            stringResource(Res.string.reset_password_forgot_your_password),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1,
        )
        Text(
            stringResource(Res.string.reset_password_enter_email),
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
        )
        Spacer(modifier = Modifier.weight(1f))
        Email(
            modifier = Modifier.focusRequester(focusRequester),
            emailState = state.email,
            keyboardOptions =
                TextFieldWithLabelDefaults.KeyboardOptions.EMAIL.copy(imeAction = ImeAction.Go),
            keyboardActions =
                KeyboardActions(
                    onGo = {
                        keyboardController?.hide()
                        if (state.submitButtonEnabled) {
                            eventSink(ResetPasswordUiEvent.EnterEmailUiEvent.OnSubmit)
                        }
                    },
                ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            text = stringResource(Res.string.reset_password_enter_email_continue),
            onClick = { eventSink(ResetPasswordUiEvent.EnterEmailUiEvent.OnSubmit) },
            enabled = state.submitButtonEnabled,
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun EnterCodeContent(
    state: ResetPasswordScreenUiState.EnterVerificationCode,
    modifier: Modifier,
    eventLogger: NewmAppEventLogger,
) {
    val eventSink = state.eventSink
    LaunchedEffect(Unit) { eventLogger.logPageLoad(AppScreens.ResetPasswordEnterCodeScreen.name) }
    EmailVerificationContent(
        modifier = modifier,
        verificationCode = state.code,
        errorMessage = state.errorMessage,
        nextButtonEnabled = state.submitButtonEnabled,
        onNextClicked = { eventSink(ResetPasswordUiEvent.EnterVerificationCodeUiEvent.OnSubmit) },
    )
}

@Composable
private fun SetNewPasswordContent(
    state: ResetPasswordScreenUiState.EnterNewPassword,
    modifier: Modifier = Modifier,
    eventLogger: NewmAppEventLogger,
) {
    val onEvent = state.eventSink
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) { eventLogger.logPageLoad(AppScreens.NewPasswordScreen.name) }

    Column(modifier = modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(Res.string.reset_password_enter_new_password),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.weight(1f))
        Password(
            modifier = Modifier.focusRequester(focusRequester),
            label = Res.string.reset_password_new_password,
            passwordState = state.password,
            keyboardOptions =
                TextFieldWithLabelDefaults.KeyboardOptions.PASSWORD.copy(imeAction = ImeAction.Next),
        )

        Password(
            label = Res.string.reset_password_confirm_new_password,
            passwordState = state.confirmPasswordState,
            keyboardOptions =
                TextFieldWithLabelDefaults.KeyboardOptions.PASSWORD.copy(imeAction = ImeAction.Go),
            keyboardActions =
                KeyboardActions(
                    onGo = {
                        keyboardController?.hide()
                        if (state.submitButtonEnabled) {
                            onEvent(ResetPasswordUiEvent.EnterNewPasswordUiEvent.OnSubmit)
                        }
                    },
                ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.confirm),
            onClick = { onEvent(ResetPasswordUiEvent.EnterNewPasswordUiEvent.OnSubmit) },
            enabled = state.submitButtonEnabled,
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

class ResetPasswordUiFactory
    @Inject
    constructor(
        private val eventLogger: NewmAppEventLogger,
    ) : Ui.Factory {
        override fun create(
            screen: Screen,
            context: CircuitContext,
        ): Ui<*>? =
            when (screen) {
                is ResetPasswordScreen -> {
                    ui<ResetPasswordScreenUiState> { state, modifier ->
                        ResetPasswordScreenUi(state, eventLogger, modifier)
                    }
                }

                else -> {
                    null
                }
            }
    }
