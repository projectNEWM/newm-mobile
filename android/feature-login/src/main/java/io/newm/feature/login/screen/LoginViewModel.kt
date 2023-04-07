package io.newm.feature.login.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import io.newm.shared.login.models.LoginStatus
import io.newm.shared.login.models.isValid
import io.newm.shared.login.usecases.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val useCase: LoginUseCase) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState>
        get() = _state.asStateFlow()

    init {
        Logger.d { "NewmAndroid - LoginViewModel" }
    }

    fun attemptToLogin(email: String, password: String) {
        Logger.d { "NewmAndroid - LoginViewModel:attemptToLogin email: $email" }
        if (email.isNotBlank() && password.isNotBlank()) {
            viewModelScope.launch {
                _state.value = _state.value.copy(
                    wrongPassword = false,
                    errorMessage = ""
                )
                when (val status: LoginStatus = useCase.logIn(email, password)) {
                    is LoginStatus.Success -> {
                        _state.value = _state.value.copy(
                            isUserLoggedIn = status.data.isValid(),
                            errorMessage = null
                        )
                        Logger.d { "NewmAndroid - LoginViewModel LoginStatus Success" }
                    }
                    is LoginStatus.WrongPassword -> {
                        _state.value = _state.value.copy(
                            wrongPassword = true,
                            errorMessage = "invalid Password"
                        )
                        Logger.d { "NewmAndroid - LoginViewModel LoginStatus invalid Password" }
                    }
                    is LoginStatus.UserNotFound -> {
                        _state.value = _state.value.copy(
                            emailNotFound = true,
                            errorMessage = "An account for: $email Doesn't exist! Please sign up first!"
                        )
                        Logger.d { "NewmAndroid - LoginViewModel LoginStatus UserNotFound" }
                    }
                    else -> {
                        // no-op
                    }
                }
            }
        }
    }

    data class LoginState(
        val email: String? = null,
        val password: String? = null,
        val emailNotFound: Boolean = false,
        val wrongPassword: Boolean = false,
        val isUserLoggedIn: Boolean = false,
        val errorMessage: String? = null
    )
}
