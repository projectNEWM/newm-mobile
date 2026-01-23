package io.newm.sharedfeatures.screens.auth.resetpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens
import io.newm.shared.commonPublic.usecases.LoginUseCase
import io.newm.shared.commonPublic.usecases.ResetPasswordUseCase
import io.newm.shared.commonPublic.usecases.SignupUseCase
import io.newm.sharedfeatures.screens.HomeScreen
import io.newm.sharedfeatures.screens.auth.login.ConfirmPasswordState
import io.newm.sharedfeatures.screens.auth.login.EmailState
import io.newm.sharedfeatures.screens.auth.login.PasswordState
import io.newm.sharedfeatures.screens.auth.login.RecaptchaManager
import io.newm.sharedfeatures.screens.auth.login.UiMessage
import io.newm.sharedfeatures.screens.auth.login.VerificationCodeState
import io.newm.sharedfeatures.screens.auth.resetpassword.ResetPasswordUiEvent.EnterEmailUiEvent
import io.newm.sharedfeatures.screens.auth.resetpassword.ResetPasswordUiEvent.EnterNewPasswordUiEvent
import io.newm.sharedfeatures.screens.auth.resetpassword.ResetPasswordUiEvent.EnterVerificationCodeUiEvent
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import newm_mobile.sharedfeatures.generated.resources.Res
import newm_mobile.sharedfeatures.generated.resources.password_reset_successfully_message

private enum class ResetPasswordStep {
    EnterEmail,
    EnterVerificationCode,
    EnterNewPassword,
}

@Inject
class ResetPasswordScreenPresenter(
    @Assisted private val screen: ResetPasswordScreen,
    @Assisted private val navigator: Navigator,
    private val signupUseCase: SignupUseCase,
    private val loginUseCase: LoginUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val recaptchaManager: RecaptchaManager,
    private val logger: NewmAppLogger,
    private val analyticsTracker: NewmAppEventLogger,
) : Presenter<ResetPasswordScreenUiState> {
    @Composable
    override fun present(): ResetPasswordScreenUiState {
        var step by rememberRetained { mutableStateOf(ResetPasswordStep.EnterEmail) }
        var isLoading by rememberRetained { mutableStateOf(false) }
        var errorMessage by rememberRetained { mutableStateOf<UiMessage?>(null) }
        val email = rememberRetained { EmailState(defaultValue = screen.email.orEmpty()) }
        val authCode = rememberRetained { VerificationCodeState() }
        val password = remember { PasswordState() }
        val passwordConfirmation = remember(password) { ConfirmPasswordState(password) }
        val coroutineScope = rememberCoroutineScope()

        return when (step) {
            ResetPasswordStep.EnterEmail -> {
                ResetPasswordScreenUiState.EnterEmail(
                    email = email,
                    errorMessage = errorMessage,
                    isLoading = isLoading,
                    submitButtonEnabled = email.isValid && !isLoading,
                    eventSink = { event ->
                        when (event) {
                            EnterEmailUiEvent.OnSubmit -> {
                                analyticsTracker.logClickEvent(
                                    AppScreens.ResetPasswordEnterEmailScreen.CONTINUE_BUTTON,
                                )
                                errorMessage = null
                                isLoading = true
                                coroutineScope.launch {
                                    try {
                                        recaptchaManager
                                            .execute("auth_code")
                                            .onSuccess { token ->
                                                signupUseCase.requestEmailConfirmationCode(
                                                    email.text,
                                                    humanVerificationCode = token,
                                                    mustExists = true,
                                                )
                                                step = ResetPasswordStep.EnterVerificationCode
                                            }.onFailure {
                                                logger.error(
                                                    "ResetPasswordScreenPresenter",
                                                    "Human verification error",
                                                    it,
                                                )
                                            }
                                    } catch (e: Throwable) {
                                        logger.error(
                                            "ResetPasswordScreenPresenter",
                                            "Requesting email confirmation code failed",
                                            e,
                                        )
                                        errorMessage = UiMessage.Text(e.message ?: "Unknown error")
                                    }

                                    isLoading = false
                                }
                            }
                        }
                    },
                )
            }

            ResetPasswordStep.EnterVerificationCode -> {
                ResetPasswordScreenUiState.EnterVerificationCode(
                    code = authCode,
                    errorMessage = errorMessage,
                    isLoading = isLoading,
                    submitButtonEnabled = authCode.isValid && !isLoading,
                    eventSink = { event ->
                        when (event) {
                            EnterVerificationCodeUiEvent.OnSubmit -> {
                                analyticsTracker.logClickEvent(
                                    AppScreens.ResetPasswordEnterCodeScreen.CONTINUE_BUTTON,
                                )
                                step = ResetPasswordStep.EnterNewPassword
                            }
                        }
                    },
                )
            }

            ResetPasswordStep.EnterNewPassword -> {
                val submitEnabled = password.isValid && passwordConfirmation.isValid && !isLoading

                ResetPasswordScreenUiState.EnterNewPassword(
                    password = password,
                    confirmPasswordState = passwordConfirmation,
                    errorMessage = errorMessage,
                    isLoading = isLoading,
                    submitButtonEnabled = submitEnabled,
                    eventSink = { event ->
                        when (event) {
                            EnterNewPasswordUiEvent.OnSubmit -> {
                                analyticsTracker.logClickEvent(
                                    AppScreens.NewPasswordScreen.CONFIRM_BUTTON,
                                )
                                errorMessage = null
                                isLoading = true
                                coroutineScope.launch {
                                    try {
                                        recaptchaManager
                                            .execute("password_reset")
                                            .onSuccess { token ->
                                                resetPasswordUseCase.resetPassword(
                                                    email = email.text,
                                                    code = authCode.text,
                                                    newPassword = password.text,
                                                    confirmPassword = passwordConfirmation.text,
                                                    humanVerificationCode = token,
                                                )
                                                errorMessage =
                                                    UiMessage.Resource(
                                                        Res.string
                                                            .password_reset_successfully_message,
                                                    )
                                                recaptchaManager.executeLogin().onSuccess { newToken ->
                                                    loginUseCase.logIn(
                                                        email.text,
                                                        password.text,
                                                        humanVerificationCode = newToken,
                                                    )
                                                    navigator.goTo(HomeScreen)
                                                }
                                            }.onFailure {
                                                logger.error(
                                                    "ResetPasswordScreenPresenter",
                                                    "Human verification error",
                                                    it,
                                                )
                                            }
                                    } catch (e: Throwable) {
                                        logger.error(
                                            "ResetPasswordScreenPresenter",
                                            "Resetting password failed",
                                            e,
                                        )
                                        errorMessage = UiMessage.Text(e.message ?: "Unknown error")
                                    }

                                    isLoading = false
                                }
                            }
                        }
                    },
                )
            }
        }
    }
}

@Inject
class ResetPasswordPresenterFactory(
    private val presenter: (ResetPasswordScreen, Navigator) -> ResetPasswordScreenPresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext,
    ): Presenter<*>? =
        when (screen) {
            is ResetPasswordScreen -> presenter(screen, navigator)
            else -> null
        }
}
