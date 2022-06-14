package io.projectnewm.feature.login.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.projectnewm.shared.login.usecases.SignupUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignupViewModel(private val useCase: SignupUseCase) : ViewModel() {

    init {
        println("cje466 Initializing SignupViewModel")
    }

    private val _state = MutableStateFlow(SignupUserState())
    val state: StateFlow<SignupUserState>
        get() = _state.asStateFlow()


    fun setUserEmail(email: String) {
        _state.value = _state.value.copy(
            email = email
        )
    }

    fun setUserPassword(password: String) {
        _state.value = _state.value.copy(
            password = password
        )
    }

    fun setUserPasswordConfirmation(passwordConfirmation: String) {
        _state.value = _state.value.copy(
            passwordConfirmation = passwordConfirmation
        )
    }

    fun setEmailVerificationCode(emailVerificationCode: String) {
        _state.value = _state.value.copy(
            emailVerificationCode = emailVerificationCode
        )
    }

    fun requestCode() {
        viewModelScope.launch {
            _state.value.let {
                if (it.email?.isEmpty() == false && it.password == it.passwordConfirmation) {
                    val response = useCase.requestEmailConfirmationCode(it.email)
                    _state.value = _state.value.copy(
                        verificationRequested = true
                    )
                    println("cje466: response: $response")
                } else {
                    println("cje466: Fields are not correct, please enter email and passwords")
                }
            }
        }
    }

    fun verifyAccount() {
//        if (!_state.value.email.isNullOrBlank() && !_state.value.password.isNullOrBlank() && !_state.value.passwordConfirmation.isNullOrBlank())
            println("cje466: email: ${_state.value.email.orEmpty()}")
            println("cje466: password: ${_state.value.password.orEmpty()}")
            println("cje466: passwordConfirmation: ${_state.value.passwordConfirmation.orEmpty()}")
            println("cje466: emailVerificationCode: ${_state.value.emailVerificationCode.orEmpty()}")
            viewModelScope.launch {
                useCase.registerUser(
                    email = _state.value.email.orEmpty(),
                    password = _state.value.password.orEmpty(),
                    passwordConfirmation = _state.value.passwordConfirmation.orEmpty(),
                    verificationCode = _state.value.emailVerificationCode.orEmpty()
                )
            }
    }

    data class SignupUserState(
        val email: String? = null,
        val password: String? = null,
        val passwordConfirmation: String? = null,
        val emailVerificationCode: String? = null,
        val verificationRequested: Boolean = false
    )
}