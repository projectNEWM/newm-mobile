package io.newm.feature.login.screen.resetpassword

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.google.android.recaptcha.RecaptchaAction
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.feature.login.screen.LoginScreen
import io.newm.feature.login.screen.authproviders.RecaptchaClientProvider
import io.newm.feature.login.screen.email.EmailState
import io.newm.feature.login.screen.password.ConfirmPasswordState
import io.newm.feature.login.screen.password.PasswordState
import io.newm.feature.login.screen.password.VerificationCodeState
import io.newm.feature.login.screen.resetpassword.ResetPasswordUiEvent.EnterEmailUiEvent
import io.newm.feature.login.screen.resetpassword.ResetPasswordUiEvent.EnterNewPasswordUiEvent
import io.newm.feature.login.screen.resetpassword.ResetPasswordUiEvent.EnterVerificationCodeUiEvent
import io.newm.shared.public.usecases.ResetPasswordUseCase
import io.newm.shared.public.usecases.SignupUseCase
import kotlinx.coroutines.launch

private enum class ResetPasswordStep {
    EnterEmail,
    EnterVerificationCode,
    EnterNewPassword,
}

class ResetPasswordScreenPresenter(
    private val navigator: Navigator,
    private val signupUseCase: SignupUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val recaptchaClientProvider: RecaptchaClientProvider
) : Presenter<ResetPasswordScreenUiState> {
    @Composable
    override fun present(): ResetPasswordScreenUiState {
        var step by remember { mutableStateOf(ResetPasswordStep.EnterEmail) }
        var isLoading by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf<String?>(null) }
        val email = rememberRetained { EmailState() }
        val authCode = rememberRetained { VerificationCodeState() }
        val password = remember { PasswordState() }
        val passwordConfirmation = remember(password) { ConfirmPasswordState(password) }
        val coroutineScope = rememberCoroutineScope()

        return when (step) {
            ResetPasswordStep.EnterEmail -> ResetPasswordScreenUiState.EnterEmail(
                email = email,
                errorMessage = errorMessage,
                isLoading = isLoading,
                submitButtonEnabled = email.isValid && !isLoading,
                eventSink = { event ->
                    when (event) {
                        EnterEmailUiEvent.OnSubmit -> {
                            errorMessage = null
                            isLoading = true
                            coroutineScope.launch {
                                try {
                                    recaptchaClientProvider.get().execute(RecaptchaAction.custom("RequestCode")).onSuccess { token ->
                                        signupUseCase.requestEmailConfirmationCode(email.text, humanVerificationCode = token)
                                        step = ResetPasswordStep.EnterVerificationCode
                                    }.onFailure {
                                        Log.e("ResetPasswordScreenPresenter", "Human verification error", it)
                                    }
                                } catch (e: Throwable) {
                                    errorMessage = e.message
                                }

                                isLoading = false
                            }
                        }
                    }
                },
            )

            ResetPasswordStep.EnterVerificationCode -> ResetPasswordScreenUiState.EnterVerificationCode(
                code = authCode,
                errorMessage = errorMessage,
                isLoading = isLoading,
                submitButtonEnabled = authCode.isValid && !isLoading,
                eventSink = { event ->
                    when (event) {
                        EnterVerificationCodeUiEvent.OnSubmit -> {
                            step = ResetPasswordStep.EnterNewPassword
                        }
                    }
                },
            )

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
                                errorMessage = null
                                isLoading = true
                                coroutineScope.launch {
                                    try {
                                        recaptchaClientProvider.get().execute(RecaptchaAction.custom("ResetPassword")).onSuccess { token ->
                                            resetPasswordUseCase.resetPassword(
                                                email = email.text,
                                                code = authCode.text,
                                                newPassword = password.text,
                                                confirmPassword = passwordConfirmation.text,
                                                humanVerificationCode = token
                                            )
                                            errorMessage = "Password reset successfully"
                                            navigator.goTo(LoginScreen)
                                        }.onFailure {
                                            Log.e("ResetPasswordScreenPresenter", "Human verification error", it)
                                        }
                                    } catch (e: Throwable) {
                                        errorMessage = e.message
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
