package io.newm.sharedfeatures.devmenu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import io.newm.sharedfeatures.screens.DevMenuItem
import io.newm.sharedfeatures.screens.DevMenuMainScreen
import io.newm.sharedfeatures.screens.FeatureFlagsListScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

class DevMenuPresenter @Inject constructor(
    @Assisted private val navigator: Navigator,
) : Presenter<DevMenuMainScreen.UiState> {

    @Composable
    override fun present(): DevMenuMainScreen.UiState {
        var navigationInProgress by remember { mutableStateOf(false) }

        val menuItems = produceState(initialValue = emptyList()) {
            value = listOf(
                DevMenuItem(
                    title = "Feature Flags",
                    screen = FeatureFlagsListScreen,
                    description = "Manage and test feature flags"
                ),
                // Add more items as needed
            )
        }.value

        return DevMenuMainScreen.UiState.Content(
            menuItems = menuItems,
            onEvent = { event ->
                when (event) {
                    is DevMenuMainScreen.UiEvent.OnItemClick -> {
                        // Fix navigation issue with debouncing
                        if (!navigationInProgress) {
                            navigationInProgress = true
                            try {
                                navigator.goTo(event.screen)
                            } catch (e: Exception) {
                                println("Navigation error: ${e.message}")
                            }
                            // Reset navigation state after delay
                            kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
                                delay(1000)
                                navigationInProgress = false
                            }
                        }
                    }
                    DevMenuMainScreen.UiEvent.OnBack -> {
                        if (!navigationInProgress) {
                            navigator.pop()
                        }
                    }
                }
            },
        )
    }
}

class DevMenuPresenterFactory @Inject constructor(
    private val presenter: (Navigator) -> DevMenuPresenter,
    private val featureFlagsListPresenter: (Navigator) -> FeatureFlagsListPresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext,
    ): Presenter<*>? {
        return when (screen) {
            is DevMenuMainScreen -> presenter(navigator)
            is FeatureFlagsListScreen -> featureFlagsListPresenter(navigator)
            else -> null
        }
    }
}