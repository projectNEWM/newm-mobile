package io.newm.sharedfeatures.welcome

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import io.newm.sharedfeatures.screens.DevMenuMainScreen
import io.newm.sharedfeatures.screens.LoginScreen
import io.newm.sharedfeatures.screens.WelcomeScreen
import io.newm.sharedfeatures.screens.WelcomeScreen.UiState
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

class WelcomePresenter @Inject constructor(
    @Assisted private val navigator: Navigator,
) : Presenter<UiState> {

    @Composable
    override fun present(): UiState {
        return UiState.Content(
            onEvent = { event ->
                when (event) {
                    WelcomeScreen.UiEvent.OnBack -> {
                        navigator.pop()
                    }

                    WelcomeScreen.UiEvent.OnDevMenu -> {
                        navigator.goTo(DevMenuMainScreen)
                    }

                    WelcomeScreen.UiEvent.OnLogin -> {
                        navigator.goTo(LoginScreen)
                    }
                }
            },
        )
    }
}

class WelcomePresenterFactory @Inject constructor(
    private val presenter: (Navigator) -> WelcomePresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext,
    ): Presenter<*>? {
        return when (screen) {
            is WelcomeScreen -> presenter(navigator)
            else -> null
        }
    }
}