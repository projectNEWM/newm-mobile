package io.newm.feature.login.screen.createaccount

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.feature.login.screen.TextFieldState
import io.newm.feature.login.screen.createaccount.CreateAccountUiState.EmailAndPasswordUiState
import io.newm.feature.login.screen.createaccount.CreateAccountUiState.EmailVerificationUiState
import io.newm.feature.login.screen.createaccount.CreateAccountUiState.SetNameUiState
import io.newm.feature.login.screen.email.EmailState
import io.newm.feature.login.screen.password.ConfirmPasswordState
import io.newm.feature.login.screen.password.PasswordState
import io.newm.shared.login.usecases.SignupUseCase
import kotlinx.coroutines.launch

class CreateAccountScreenPresenter constructor(
    private val navigateHome: () -> Unit,
    private val signupUseCase: SignupUseCase,
) : Presenter<CreateAccountUiState> {
    @Composable
    override fun present(): CreateAccountUiState {
        var step by rememberRetained { mutableStateOf(Step.EmailAndPassword) }
        val userEmail = rememberRetained { EmailState() }
        val password = rememberRetained { PasswordState() }
        val passwordConfirmation = rememberRetained { ConfirmPasswordState(password) }
        val name = rememberRetained { TextFieldState() }
        val verificationCode = rememberRetained { TextFieldState() }

        val coroutineScope = rememberCoroutineScope()

        val emailAndPasswordValid = remember(userEmail.isValid, password.isValid, passwordConfirmation.isValid) {
            userEmail.isValid && password.isValid && passwordConfirmation.isValid
        }

        return when (step) {
            Step.EmailAndPassword -> {
                EmailAndPasswordUiState(
                    emailState = userEmail,
                    passwordState = password,
                    passwordConfirmationState = passwordConfirmation,
                ) { event ->
                    when (event) {
                        SignupFormUiEvent.Next -> {
                            if (!emailAndPasswordValid) return@EmailAndPasswordUiState // TODO handle error

                            coroutineScope.launch {
                                step = Step.Loading
                                step = try {
                                    signupUseCase.requestEmailConfirmationCode(
                                        email = userEmail.text,
                                    )
                                    Step.SetName
                                } catch (e: Throwable) {
                                    // TODO handle error
                                    Step.EmailAndPassword
                                }
                            }
                        }
                    }
                }
            }

            Step.EmailVerification -> {
                val nextEnabled = remember(emailAndPasswordValid, verificationCode.isValid, name.isValid) {
                    emailAndPasswordValid && verificationCode.isValid && name.isValid
                }

                EmailVerificationUiState(
                    verificationCode = verificationCode,
                ) { event ->
                    when (event) {
                        is EmailVerificationUiEvent.Next -> {
                            if (!nextEnabled) return@EmailVerificationUiState // TODO handle error

                            coroutineScope.launch {
                                step = Step.Loading
                                try {
                                    signupUseCase.registerUser(
                                        email = userEmail.text,
                                        verificationCode = verificationCode.text,
                                        password = password.text,
                                        passwordConfirmation = passwordConfirmation.text,
                                        nickname = name.text,
                                    )
                                    navigateHome()

                                } catch (e: Throwable) {
                                    step = Step.EmailVerification
                                    // TODO handle error
                                    e.printStackTrace()
                                }
                            }
                        }
                    }
                }
            }

            Step.SetName -> SetNameUiState(
                name = name,
            ) { event ->
                when (event) {
                    SetNameUiEvent.Next -> {
                        if (!name.isValid) return@SetNameUiState // TODO handle error

                        step = Step.EmailVerification
                    }
                }
            }

            Step.Loading -> CreateAccountUiState.Loading
        }
    }
}

private enum class Step {
    Loading,
    EmailAndPassword,
    EmailVerification,
    SetName,
}
