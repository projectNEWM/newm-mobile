package io.newm.sharedfeatures.screens.auth.signup

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
import io.newm.shared.commonPublic.usecases.SignupUseCase
import io.newm.sharedfeatures.screens.HomeScreen
import io.newm.sharedfeatures.screens.auth.login.ConfirmPasswordState
import io.newm.sharedfeatures.screens.auth.login.EmailState
import io.newm.sharedfeatures.screens.auth.login.PasswordState
import io.newm.sharedfeatures.screens.auth.login.RecaptchaManager
import io.newm.sharedfeatures.screens.auth.login.UiMessage
import io.newm.sharedfeatures.screens.auth.login.VerificationCodeState
import io.newm.sharedfeatures.screens.auth.signup.CreateAccountUiState.EmailAndPasswordUiState
import io.newm.sharedfeatures.screens.auth.signup.CreateAccountUiState.EmailVerificationUiState
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import newm_mobile.sharedfeatures.generated.resources.Res
import newm_mobile.sharedfeatures.generated.resources.invalid_recaptcha_message

@Inject
class CreateAccountScreenPresenter(
    @Assisted private val navigator: Navigator,
    private val signupUseCase: SignupUseCase,
    private val loginUseCase: LoginUseCase,
    private val recaptchaManager: RecaptchaManager,
    private val appLogger: NewmAppLogger,
    private val eventLogger: NewmAppEventLogger,
) : Presenter<CreateAccountUiState> {
    @Composable
    override fun present(): CreateAccountUiState {
        var step by rememberRetained { mutableStateOf(Step.EmailAndPassword) }
        val userEmail = rememberRetained { EmailState() }
        val password = rememberRetained { PasswordState() }
        val passwordConfirmation = rememberRetained { ConfirmPasswordState(password) }
        val verificationCode = rememberRetained { VerificationCodeState() }

        val coroutineScope = rememberCoroutineScope()

        val emailAndPasswordValid =
            remember(userEmail.isValid, password.isValid, passwordConfirmation.isValid) {
                userEmail.isValid && password.isValid && passwordConfirmation.isValid
            }

        var errorMessage by remember { mutableStateOf<UiMessage?>(null) }

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
                            eventLogger.logClickEvent(AppScreens.CreateAccountScreen.NEXT_BUTTON)
                            require(emailAndPasswordValid) {
                                "Email and password - next button should not be enabled if any of the fields are invalid"
                            }

                            coroutineScope.launch {
                                step = Step.Loading
                                step =
                                    try {
                                        recaptchaManager
                                            .execute("auth_code")
                                            .onSuccess { token ->
                                                signupUseCase.requestEmailConfirmationCode(
                                                    email = userEmail.text,
                                                    humanVerificationCode = token,
                                                )
                                            }.onFailure {
                                                errorMessage =
                                                    UiMessage.Resource(
                                                        Res.string.invalid_recaptcha_message,
                                                    )
                                            }
                                        Step.EmailVerification
                                    } catch (e: Throwable) {
                                        appLogger.error(
                                            tag = "Sign up",
                                            message = "${e.message}",
                                            exception = e,
                                        )
                                        errorMessage = UiMessage.Text(e.message ?: "Unknown error")
                                        Step.EmailAndPassword
                                    }
                            }
                        }
                    }
                }
            }

            Step.EmailVerification -> {
                eventLogger.logPageLoad(AppScreens.EmailVerificationScreen.name)
                EmailVerificationUiState(
                    verificationCode = verificationCode,
                    nextButtonEnabled = verificationCode.isValid,
                    errorMessage = errorMessage,
                ) { event ->
                    when (event) {
                        is EmailVerificationUiEvent.Next -> {
                            require(emailAndPasswordValid && verificationCode.isValid) {
                                "Email verification - next button should not be enabled if any of the fields are invalid"
                            }
                            eventLogger.logClickEvent(
                                AppScreens.EmailVerificationScreen.CONTINUE_BUTTON,
                            )

                            coroutineScope.launch {
                                step = Step.Loading
                                try {
                                    recaptchaManager
                                        .execute("signup")
                                        .onSuccess { token ->
                                            signupUseCase.registerUser(
                                                email = userEmail.text,
                                                verificationCode = verificationCode.text,
                                                password = password.text,
                                                passwordConfirmation = passwordConfirmation.text,
                                                humanVerificationCode = token,
                                            )
                                            recaptchaManager.executeLogin().onSuccess { newToken ->
                                                loginUseCase.logIn(
                                                    userEmail.text,
                                                    password.text,
                                                    humanVerificationCode = newToken,
                                                )
                                                navigator.goTo(HomeScreen)
                                            }
                                        }.onFailure {
                                            errorMessage =
                                                UiMessage.Resource(
                                                    Res.string.invalid_recaptcha_message,
                                                )
                                        }
                                } catch (e: Throwable) {
                                    appLogger.error(
                                        tag = "Create Account",
                                        message = "Email verification: ${e.message}",
                                        exception = e,
                                    )
                                    errorMessage = UiMessage.Text(e.message ?: "Unknown error")
                                    step = Step.EmailVerification
                                }
                            }
                        }
                    }
                }
            }

            Step.Loading -> {
                eventLogger.logPageLoad(AppScreens.LoadingScreen.name)
                CreateAccountUiState.Loading
            }
        }
    }
}

@Inject
class CreateAccountPresenterFactory(
    private val presenter: (Navigator) -> CreateAccountScreenPresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext,
    ): Presenter<*>? =
        when (screen) {
            is CreateAccountScreen -> presenter(navigator)
            else -> null
        }
}

private enum class Step {
    Loading,
    EmailAndPassword,
    EmailVerification,
}
