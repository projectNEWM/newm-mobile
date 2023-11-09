package io.newm.feature.login.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import io.newm.shared.login.models.LoginException
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.LoginUseCase
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
        if (email.sanitize().isNotBlank() && password.sanitize().isNotBlank()) {
            viewModelScope.launch {
                _state.value = _state.value.copy(
                    wrongPassword = false,
                    errorMessage = ""
                )
                try {
                    useCase.logIn(email, password)
                    _state.value = _state.value.copy(
                        isUserLoggedIn = true,
                        errorMessage = null
                    )
                    Logger.d { "NewmAndroid - LoginViewModel LoginStatus Success" }
                } catch (e: LoginException.WrongPassword) {
                    _state.value = _state.value.copy(
                        wrongPassword = true,
                        errorMessage = "invalid Password"
                    )
                    Logger.d { "NewmAndroid - LoginViewModel LoginStatus invalid Password" }
                } catch (e: LoginException.UserNotFound) {
                    _state.value = _state.value.copy(
                        emailNotFound = true,
                        errorMessage = "An account for: $email Doesn't exist! Please sign up first!"
                    )
                    Logger.d { "NewmAndroid - LoginViewModel LoginStatus UserNotFound" }
                } catch (e: KMMException) {
                    Logger.d { "NewmAndroid - LoginViewModel LoginStatus UnknownError" }
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

    private fun String.sanitize(): String {
        return this.trim().replace("\n", "")
    }
}
