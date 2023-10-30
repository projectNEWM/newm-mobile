package io.newm.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import io.newm.Logout
import io.newm.shared.models.User
import io.newm.shared.usecases.UserProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val userProviderUserCase: UserProfileUseCase, private val logout: Logout) : ViewModel() {

    private var _state = MutableStateFlow<ProfileState>(ProfileState.Loading)

    val state: StateFlow<ProfileState>
        get() = _state.asStateFlow()

    init {
        println("NewmAndroid - ProfileViewModel")
        viewModelScope.launch {
            val user = userProviderUserCase.getCurrentUser()
            Logger.d { "NewmAndroid - ProfileViewModel user: $user" }
            _state.value = ProfileState.Content(profile = user)
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            logout.call()
        }
    }

}

sealed class ProfileState {
    object Loading : ProfileState()
    data class Content(val profile: User) : ProfileState()
}