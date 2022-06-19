package io.projectnewm.feature.login.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.projectnewm.shared.login.models.RegisterStatus
import io.projectnewm.shared.login.usecases.SignupUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignupViewModel(private val useCase: SignupUseCase) : ViewModel() {

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
                }
            }
        }
    }

    fun verifyAccount() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                errorMessage = null
            )

            val status = useCase.registerUser(
                email = _state.value.email.orEmpty(),
                password = _state.value.password.orEmpty(),
                passwordConfirmation = _state.value.passwordConfirmation.orEmpty(),
                verificationCode = _state.value.emailVerificationCode.orEmpty()
            )

            when (status) {
                is RegisterStatus.Success -> {
                    _state.value = _state.value.copy(
                        isUserRegistered = true,
                        errorMessage = null
                    )
                }
                is RegisterStatus.UserAlreadyExists -> {
                    _state.value = _state.value.copy(
                        errorMessage = "User associated with that Email Already Exists"
                    )
                }
                is RegisterStatus.UnknownError -> {
                    _state.value = _state.value.copy(
                        errorMessage = "Unknown Error"
                    )
                }
                is RegisterStatus.TwoFactorAuthenticationFailed -> {
                    _state.value = _state.value.copy(
                        errorMessage = "Wrong Verification Code"
                    )
                }
            }
        }
    }

    data class SignupUserState(
        val email: String? = null,
        val password: String? = null,
        val passwordConfirmation: String? = null,
        val emailVerificationCode: String? = null,
        val verificationRequested: Boolean = false,
        val isUserRegistered: Boolean = false,
        val errorMessage: String? = null
    )
}