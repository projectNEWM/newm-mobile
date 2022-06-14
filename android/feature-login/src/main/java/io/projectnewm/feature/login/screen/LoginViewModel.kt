package io.projectnewm.feature.login.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.projectnewm.shared.login.usecases.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val useCase: LoginUseCase) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState>
        get() = _state.asStateFlow()

    fun loginUserWith(email: String, password: String) {
        viewModelScope.launch {
            val canTheUserLogin = useCase.logIn(email, password)
            _state.value = _state.value.copy(
                isUserLoggedIn = canTheUserLogin
            )
            println("cje466: LoginViewModel: response: $canTheUserLogin")
        }
    }

    data class LoginState(
        val isUserLoggedIn: Boolean = false
    )
}
