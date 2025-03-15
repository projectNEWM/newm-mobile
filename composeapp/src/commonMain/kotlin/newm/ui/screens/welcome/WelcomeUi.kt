package newm.ui.screens.welcome

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject
import newm.ui.screens.welcome.WelcomeScreen.UiState


@Composable
fun WelcomeUi(state: UiState, modifier: Modifier) {
    when (state) {
        is UiState.Content -> {
            Text("Welcome to Newm!")
        }
        UiState.Loading -> {
            Text("Welcome to Newm!")
        }
    }
}

class WelcomeUiFactory @Inject constructor() : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
        return when (screen) {
            WelcomeScreen -> ui<UiState> { state, modifier ->
                WelcomeUi(state, modifier)
            }

            else -> null
        }
    }
}