package io.newm.sharedfeatures.screens.auth.login

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
import io.newm.sharedfeatures.screens.HomeScreen
import io.newm.sharedfeatures.screens.LoginScreen
import io.newm.sharedfeatures.screens.auth.resetpassword.ResetPasswordScreen
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import newm_mobile.sharedfeatures.generated.resources.Res
import newm_mobile.sharedfeatures.generated.resources.invalid_recaptcha_message
import newm_mobile.sharedfeatures.generated.resources.login_invalid_form_message
import newm_mobile.sharedfeatures.generated.resources.nft_library_error_message
import org.jetbrains.compose.resources.StringResource

@Inject
class LoginPresenter(
    @Assisted private val navigator: Navigator,
    private val loginUseCase: () -> LoginUseCase,
    private val recaptchaManager: RecaptchaManager,
    private val logger: NewmAppLogger,
    private val analyticsTracker: NewmAppEventLogger,
) : Presenter<LoginScreen.UiState> {
    @Composable
    override fun present(): LoginScreen.UiState {
        val email = rememberRetained { EmailState() }
        val password = rememberRetained { PasswordState() }
        val isFormValid =
            remember(email.isValid, password.isValid) { email.isValid && password.isValid }
        var errorMessage by remember { mutableStateOf<StringResource?>(null) }
        var isLoading by remember { mutableStateOf(false) }

        val coroutineScope = rememberCoroutineScope()

        return LoginScreen.UiState(
            emailState = email,
            passwordState = password,
            submitButtonEnabled = isFormValid && isLoading.not(),
            errorMessage = errorMessage,
            isLoading = isLoading,
            eventSink = { event ->
                when (event) {
                    LoginScreen.UiEvent.OnLoginClick -> {
                        analyticsTracker.logClickEvent(AppScreens.LogInWithEmailScreen.LOGIN_BUTTON)
                        coroutineScope.launch {
                            errorMessage = null

                            if (!isFormValid) {
                                errorMessage = Res.string.login_invalid_form_message
                                return@launch
                            }

                            isLoading = true
                            try {
                                recaptchaManager
                                    .executeLogin()
                                    .onSuccess { token ->
                                        loginUseCase()
                                            .logIn(
                                                email.text,
                                                password.text,
                                                humanVerificationCode = token,
                                            )
                                        navigator.goTo(HomeScreen)
                                    }.onFailure {
                                        errorMessage = Res.string.invalid_recaptcha_message
                                        isLoading = false
                                    }
                            } catch (e: Throwable) {
                                logger.error("LoginScreenPresenter", "Login failed", e)
                                isLoading = false
                                errorMessage = Res.string.nft_library_error_message
                            }
                        }
                    }

                    LoginScreen.UiEvent.ForgotPasswordClick -> {
                        analyticsTracker.logClickEvent(
                            AppScreens.LogInWithEmailScreen.FORGOT_PASSWORD_BUTTON,
                        )
                        navigator.goTo(ResetPasswordScreen(email.text))
                    }
                }
            },
        )
    }
}

@Inject
class LoginPresenterFactory(
    private val presenter: (Navigator) -> LoginPresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext,
    ): Presenter<*>? =
        when (screen) {
            is LoginScreen -> presenter(navigator)
            else -> null
        }
}
