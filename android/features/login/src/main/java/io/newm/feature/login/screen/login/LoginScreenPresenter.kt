package io.newm.feature.login.screen.login

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
import io.newm.feature.login.screen.ResetPasswordScreen
import io.newm.feature.login.screen.HomeScreen
import io.newm.feature.login.screen.authproviders.RecaptchaClientProvider
import io.newm.feature.login.screen.email.EmailState
import io.newm.feature.login.screen.password.PasswordState
import io.newm.shared.NewmAppLogger
import io.newm.shared.public.analytics.NewmAppEventLogger
import io.newm.shared.public.usecases.LoginUseCase
import kotlinx.coroutines.launch

class LoginScreenPresenter(
    private val navigator: Navigator,
    private val loginUseCase: LoginUseCase,
    private val recaptchaClientProvider: RecaptchaClientProvider,
    private val logger: NewmAppLogger,
    private val analyticsTracker: NewmAppEventLogger
) : Presenter<LoginScreenUiState> {
    @Composable
    override fun present(): LoginScreenUiState {
        val email = rememberRetained { EmailState() }
        val password = rememberRetained { PasswordState() }
        val isFormValid = remember(email.isValid, password.isValid) {
            email.isValid && password.isValid
        }
        var errorMessage by remember { mutableStateOf<String?>(null) }
        var isLoading by remember { mutableStateOf(false) }

        val coroutineScope = rememberCoroutineScope()

        return LoginScreenUiState(
            emailState = email,
            passwordState = password,
            submitButtonEnabled = isFormValid && isLoading.not(),
            errorMessage = errorMessage,
            isLoading = isLoading,
            eventSink = { event ->
                when (event) {
                    LoginUiEvent.OnLoginClick -> {
                        analyticsTracker.logClickEvent("Login")
                        coroutineScope.launch {
                            errorMessage = null

                            if(!isFormValid){
                                errorMessage = "Invalid form" // todo update with proper error message
                                return@launch
                            }

                            isLoading = true
                            try {
                                recaptchaClientProvider.get().execute(RecaptchaAction.LOGIN).onSuccess { token ->
                                    loginUseCase.logIn(email.text, password.text, humanVerificationCode = token)
                                    navigator.goTo(HomeScreen)
                                }.onFailure {
                                    errorMessage = "Are you even human?"
                                    isLoading = false
                                }

                            } catch (e: Throwable) {
                                logger.error("LoginScreenPresenter", "Login failed", e)
                                isLoading = false
                                errorMessage = e.message
                            }
                        }
                    }

                    LoginUiEvent.ForgotPasswordClick -> {
                        analyticsTracker.logClickEvent("Forgot your password password?")
                        navigator.goTo(ResetPasswordScreen(email.text))
                    }
                }
            }
        )
    }
}

