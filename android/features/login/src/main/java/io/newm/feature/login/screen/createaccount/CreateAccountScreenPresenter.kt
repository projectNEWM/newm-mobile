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
import io.newm.shared.public.usecases.SignupUseCase
import kotlinx.coroutines.launch

private const val MINIMUM_VERIFICATION_CODE_LENGTH = 6

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
        val name = rememberRetained {
            TextFieldState(
                validator = { it.length >= 3 },
                errorFor = { "Name must be at least 3 characters" },
            )
        }
        val verificationCode = rememberRetained {
            TextFieldState(
                validator = { it.length >= MINIMUM_VERIFICATION_CODE_LENGTH },
                errorFor = { "Verification code must be at least $MINIMUM_VERIFICATION_CODE_LENGTH characters" },
            )
        }

        val coroutineScope = rememberCoroutineScope()

        val emailAndPasswordValid =
            remember(userEmail.isValid, password.isValid, passwordConfirmation.isValid) {
                userEmail.isValid && password.isValid && passwordConfirmation.isValid
            }

        var errorMessage by remember { mutableStateOf<String?>(null) }

        return when (step) {
            Step.EmailAndPassword -> {
                EmailAndPasswordUiState(
                    emailState = userEmail,
                    passwordState = password,
                    submitButtonEnabled = emailAndPasswordValid,
                    passwordConfirmationState = passwordConfirmation,
                    errorMessage = errorMessage,
                ) { event ->
                    when (event) {
                        SignupFormUiEvent.Next -> {
                            require(emailAndPasswordValid) {
                                "Email and password - next button should not be enabled if any of the fields are invalid"
                            }

                            coroutineScope.launch {
                                step = Step.Loading
                                step = try {
                                    signupUseCase.requestEmailConfirmationCode(
                                        email = userEmail.text,
                                    )
                                    Step.SetName
                                } catch (e: Throwable) {
                                    errorMessage = e.message
                                    Step.EmailAndPassword
                                }
                            }
                        }
                    }
                }
            }

            Step.EmailVerification -> {
                EmailVerificationUiState(
                    verificationCode = verificationCode,
                    nextButtonEnabled = verificationCode.isValid,
                    errorMessage = errorMessage,
                ) { event ->
                    when (event) {
                        is EmailVerificationUiEvent.Next -> {
                            require(emailAndPasswordValid && verificationCode.isValid && name.isValid) {
                                "Email verification - next button should not be enabled if any of the fields are invalid"
                            }

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
                                    errorMessage = e.message
                                    step = Step.EmailVerification
                                }
                            }
                        }
                    }
                }
            }

            Step.SetName -> SetNameUiState(
                name = name,
                nextButtonEnabled = name.isValid,
            ) { event ->
                when (event) {
                    SetNameUiEvent.Next -> {
                        require(name.isValid) {
                            "Name - next button should not be enabled if the name is invalid"
                        }
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
