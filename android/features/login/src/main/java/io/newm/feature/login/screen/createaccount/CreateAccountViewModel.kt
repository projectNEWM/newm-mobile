package io.newm.feature.login.screen.createaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import io.newm.shared.login.models.RegisterException
import io.newm.shared.login.repository.KMMException
import io.newm.shared.usecases.SignupUseCase
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
                    try {
                        useCase.requestEmailConfirmationCode(it.email)
                        _state.value = _state.value.copy(
                            verificationRequested = true
                        )
                    } catch (e: KMMException) {
                        print("KMMException - ${e.message}")
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

            try {
                useCase.registerUser(
                    nickname = _state.value.nickname.orEmpty(),
                    email = _state.value.email.orEmpty(),
                    password = _state.value.password.orEmpty(),
                    passwordConfirmation = _state.value.passwordConfirmation.orEmpty(),
                    verificationCode = _state.value.emailVerificationCode.orEmpty()
                )
                _state.value = _state.value.copy(
                    isUserRegistered = true,
                    errorMessage = null
                )
                Logger.d { "NewmAndroid - CreateAccountViewModel: RegisterStatus Success" }
            } catch (e: RegisterException) {
                when (e) {
                    is RegisterException.TwoFactorAuthenticationFailed -> {
                        _state.value = _state.value.copy(
                            errorMessage = "Wrong Verification Code"
                        )
                        Logger.d { "NewmAndroid - CreateAccountViewModel: RegisterStatus Wrong Verification Code" }
                    }
                    is RegisterException.UserAlreadyExists -> {
                        _state.value = _state.value.copy(
                            errorMessage = "User associated with that Email Already Exists"
                        )
                        Logger.d { "NewmAndroid - CreateAccountViewModel: RegisterStatus UserAlreadyExists" }
                    }
                }
                print("KMMException - ${e.message}")
            } catch (e: KMMException) {
                print("KMMException - ${e.message}")
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
