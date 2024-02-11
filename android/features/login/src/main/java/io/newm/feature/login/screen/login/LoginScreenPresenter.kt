package io.newm.feature.login.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.feature.login.screen.ForgotPasswordScreen
import io.newm.feature.login.screen.HomeScreen
import io.newm.feature.login.screen.email.EmailState
import io.newm.feature.login.screen.password.PasswordState
import io.newm.shared.public.usecases.LoginUseCase
import kotlinx.coroutines.launch

class LoginScreenPresenter(
    private val navigator: Navigator,
    private val loginUseCase: LoginUseCase,
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
                        coroutineScope.launch {
                            errorMessage = null

                            if(!isFormValid){
                                errorMessage = "Invalid form" // todo update with proper error message
                                return@launch
                            }

                            isLoading = true
                            try {
                                loginUseCase.logIn(email.text, password.text)
                                navigator.goTo(HomeScreen)
                            } catch (e: Throwable) {
                                isLoading = false
                                errorMessage = e.message
                            }
                        }
                    }

                    LoginUiEvent.ForgotPasswordClick -> navigator.goTo(ForgotPasswordScreen(email.text))
                }
            }
        )
    }
}
