package io.newm.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.newm.Logout
import io.newm.shared.config.NewmSharedBuildConfigImpl
import io.newm.shared.public.usecases.ConnectWalletUseCase
import io.newm.shared.public.usecases.UserDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserAccountViewModel(
    private val userDetailsUseCase: UserDetailsUseCase,
    private val connectWalletUseCase: ConnectWalletUseCase,
    private val logout: Logout
) : ViewModel() {

    private val _state: StateFlow<UserAccountState> by lazy {
        combine(
            userDetailsUseCase.fetchLoggedInUserDetailsFlow().filterNotNull(),
            connectWalletUseCase.isConnectedFlow()
        ) { user, isConnected ->
            UserAccountState.Content(
                profile = user,
                isWalletConnected = isConnected,
                eventSink = { },
                showWalletConnectButton = NewmSharedBuildConfigImpl.isStagingMode
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserAccountState.Loading)
    }
    val state: StateFlow<UserAccountState>
        get() = _state


    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            logout.signOutUser()
        }
    }

    fun disconnectWallet() {
        viewModelScope.launch(Dispatchers.IO) {
            connectWalletUseCase.disconnect()
        }
    }

    fun connectWallet(xpubKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            connectWalletUseCase.connect(xpubKey)
        }
    }
}
