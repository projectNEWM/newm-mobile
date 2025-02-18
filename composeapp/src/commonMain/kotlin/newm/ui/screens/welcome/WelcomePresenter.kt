package newm.ui.screens.welcome

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import newm.ui.screens.welcome.WelcomeScreen.UiState

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