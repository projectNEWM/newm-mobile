package io.newm.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import io.newm.Logout
import io.newm.shared.public.models.User
import io.newm.shared.public.usecases.UserDetailsUseCase
import io.newm.shared.public.usecases.ConnectWalletUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserAccountViewModel(
    private val userDetailsUseCase: UserDetailsUseCase,
    private val connectWalletUseCase: ConnectWalletUseCase,
    private val logout: Logout
) : ViewModel() {

    private var _state = MutableStateFlow<UserAccountState>(UserAccountState.Loading)

    val state: StateFlow<UserAccountState>
        get() = _state.asStateFlow()

    init {
        println("NewmAndroid - ProfileViewModel")
        viewModelScope.launch {
            val user = userDetailsUseCase.fetchLoggedInUserDetails()
            Logger.d { "NewmAndroid - ProfileViewModel user: $user" }
            _state.value = UserAccountState.Content(
                profile = user,
                isWalletConnected = connectWalletUseCase.isConnected()
            )
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            logout.call()
        }
    }

    fun disconnectWallet() {
        viewModelScope.launch(Dispatchers.IO) {
            connectWalletUseCase.disconnect()
            _state.value = UserAccountState.Content(
                profile = (state.value as UserAccountState.Content).profile,
                isWalletConnected = false
            )
        }
    }

    fun connectWallet(xpubKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            connectWalletUseCase.connect(xpubKey)
            _state.value = UserAccountState.Content(
                profile = (state.value as UserAccountState.Content).profile,
                isWalletConnected = true
            )
        }
    }
}

sealed class UserAccountState {
    data object Loading : UserAccountState()
    data class Content(val profile: User, val isWalletConnected: Boolean) : UserAccountState()
}