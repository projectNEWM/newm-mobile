package io.newm.screens.profile.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import io.newm.Logout
import io.newm.shared.public.models.User
import io.newm.shared.public.usecases.UserDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileEditViewModel(
    private val userDetailsUseCase: UserDetailsUseCase,
) : ViewModel() {

    private var _state = MutableStateFlow<ProfileState>(ProfileState.Loading)

    val state: StateFlow<ProfileState>
        get() = _state.asStateFlow()

    init {
        println("NewmAndroid - ProfileViewModel")
        viewModelScope.launch {
            val user = userDetailsUseCase.fetchLoggedInUserDetails()
            Logger.d { "NewmAndroid - ProfileViewModel user: $user" }
            _state.value = ProfileState.Content(profile = user)
        }
    }
}

sealed class ProfileState {
    data object Loading : ProfileState()
    data class Content(val profile: User) : ProfileState()
}