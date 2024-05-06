package io.newm.screens.profile.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.shared.public.usecases.UserDetailsUseCase

class ProfileEditPresenter(
    private val navigator: Navigator,
    private val userDetailsUseCase: UserDetailsUseCase
) : Presenter<ProfileState> {
    @Composable
    override fun present(): ProfileState {
        val user by remember {
            userDetailsUseCase.fetchLoggedInUserDetailsFlow()
        }.collectAsState(initial = null)

        return if (user == null) {
            ProfileState.Loading
        } else {
            ProfileState.Content(profile = user!!) { event ->
                when(event) {
                    ProfileEvent.OnBack -> navigator.pop()
                }
            }
        }
    }
}
