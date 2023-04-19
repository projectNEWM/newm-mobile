package io.newm.feature.login.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import io.newm.shared.login.models.RegisterException
import io.newm.shared.login.usecases.SignupUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateAccountViewModel(private val useCase: SignupUseCase) : ViewModel() {

    private val _state = MutableStateFlow(SignupUserState())
    val state: StateFlow<SignupUserState>
        get() = _state.asStateFlow()


    init {
        Logger.d { "NewmAndroid - CreateAccountViewModel" }
    }

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

    fun setNickName(nickname: String) {
        _state.value = _state.value.copy(
            nickname = nickname
        )
    }

    fun requestCode() {
        Logger.d { "NewmAndroid - CreateAccountViewModel: requestCode()" }

        viewModelScope.launch {
            _state.value.let {
                if (it.email?.isEmpty() == false && it.password == it.passwordConfirmation) {
                    when (useCase.requestEmailConfirmationCode(it.email)) {
                        is RequestEmailStatus.Success -> {
                            _state.value = _state.value.copy(
                                verificationRequested = true
                            )
                        }
                        else -> {
                            print("Unable to request code")
                        }
                    }

                }
            }
        }
    }

    fun verifyAccount() {
        Logger.d { "NewmAndroid - CreateAccountViewModel: verifyAccount()" }
        viewModelScope.launch {
            _state.value = _state.value.copy(
                errorMessage = null
            )

            val status = useCase.registerUser(
                nickname = _state.value.nickname.orEmpty(),
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
                    Logger.d { "NewmAndroid - CreateAccountViewModel: RegisterStatus Success" }
                }
                is RegisterStatus.UserAlreadyExists -> {
                    _state.value = _state.value.copy(
                        errorMessage = "User associated with that Email Already Exists"
                    )
                    Logger.d { "NewmAndroid - CreateAccountViewModel: RegisterStatus UserAlreadyExists" }
                }
                is RegisterStatus.UnknownError -> {
                    _state.value = _state.value.copy(
                        errorMessage = "Unknown Error"
                    )
                    Logger.d { "NewmAndroid - CreateAccountViewModel: RegisterStatus UnknownError" }
                }
                is RegisterStatus.TwoFactorAuthenticationFailed -> {
                    _state.value = _state.value.copy(
                        errorMessage = "Wrong Verification Code"
                    )
                    Logger.d { "NewmAndroid - CreateAccountViewModel: RegisterStatus Wrong Verification Code" }
                }
            }
        }
    }

    data class SignupUserState(
        val nickname: String? = null,
        val email: String? = null,
        val password: String? = null,
        val passwordConfirmation: String? = null,
        val emailVerificationCode: String? = null,
        val verificationRequested: Boolean = false,
        val isUserRegistered: Boolean = false,
        val errorMessage: String? = null
    )
}