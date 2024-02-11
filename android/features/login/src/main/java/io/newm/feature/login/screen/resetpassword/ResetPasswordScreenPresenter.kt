package io.newm.feature.login.screen.resetpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.feature.login.screen.TextFieldState
import io.newm.feature.login.screen.email.EmailState
import io.newm.feature.login.screen.password.ConfirmPasswordState
import io.newm.feature.login.screen.password.PasswordState
import io.newm.feature.login.screen.resetpassword.ResetPasswordUiEvent.EnterEmailUiEvent
import io.newm.shared.public.usecases.ChangePasswordUseCase
import io.newm.shared.public.usecases.SignupUseCase
import kotlinx.coroutines.launch

enum class ResetPasswordStep {
    EnterEmail,
    EnterVerificationCode,
    EnterNewPassword,
}

class ResetPasswordScreenPresenter(
    val navigator: Navigator,
    val signupUseCase: SignupUseCase,
    val resetPasswordUseCase: ChangePasswordUseCase,
) : Presenter<ResetPasswordScreenUiState> {
    @Composable
    override fun present(): ResetPasswordScreenUiState {
        var step by remember { mutableStateOf(ResetPasswordStep.EnterEmail) }
        var isLoading by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf<String?>(null) }
        val email = rememberRetained { EmailState() }
        val authCode = rememberRetained { TextFieldState() }
        val password = remember { PasswordState() }
        val passwordConfirmation = remember(password) { ConfirmPasswordState(password) }
        val coroutineScope = rememberCoroutineScope()

        return when (step) {
            ResetPasswordStep.EnterEmail -> ResetPasswordScreenUiState.EnterEmail(
                email = email,
                errorMessage = errorMessage,
                isLoading = isLoading,
                submitButtonEnabled = email.isValid,
                eventSink = { event ->
                    when (event) {
                        EnterEmailUiEvent.OnSubmit -> {
                            errorMessage = null
                            isLoading = true
                            coroutineScope.launch {
                                try {
                                    signupUseCase.requestEmailConfirmationCode(email.text)
                                    step = ResetPasswordStep.EnterVerificationCode
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
                submitButtonEnabled = authCode.isValid,
                eventSink = { event ->
                    TODO()
                },
            )

            ResetPasswordStep.EnterNewPassword -> ResetPasswordScreenUiState.EnterNewPassword(
                password = password,
                confirmPasswordState = passwordConfirmation,
                errorMessage = errorMessage,
                isLoading = isLoading,
                submitButtonEnabled = password.isValid && passwordConfirmation.isValid,
                eventSink = { event ->
                    TODO()
                },
            )
        }
    }
}
