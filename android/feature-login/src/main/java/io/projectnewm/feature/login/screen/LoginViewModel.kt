package io.projectnewm.feature.login.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.projectnewm.shared.login.models.LoginStatus
import io.projectnewm.shared.login.models.isValid
import io.projectnewm.shared.login.usecases.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val useCase: LoginUseCase) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState>
        get() = _state.asStateFlow()

    fun attemptToLogin(email: String, password: String) {
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
                    }
                    is LoginStatus.WrongPassword -> {
                        _state.value = _state.value.copy(
                            wrongPassword = true,
                            errorMessage = "invalid Password"
                        )
                    }
                    is LoginStatus.UserNotFound -> {
                        _state.value = _state.value.copy(
                            emailNotFound = true,
                            errorMessage = "An account for: $email Doesn't exist! Please sign up first!"
                        )
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
