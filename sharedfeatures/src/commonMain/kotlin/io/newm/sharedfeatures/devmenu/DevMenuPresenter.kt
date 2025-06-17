package io.newm.sharedfeatures.devmenu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

class DevMenuPresenter @Inject constructor(
    @Assisted private val navigator: Navigator,
) : Presenter<DevMenuMainScreen.UiState> {

    @Composable
    override fun present(): DevMenuMainScreen.UiState {
        // Using produceState to create the static list of menu items.
        // This could be made dynamic if menu items needed to be loaded asynchronously.
        val menuItems = produceState(initialValue = emptyList<DevMenuItem>()) {
            value = listOf(
                DevMenuItem(
                    title = "Feature Flags",
                    screen = FeatureFlagsListScreen
                ),
                // To add another debug option, simply add a new DevMenuItem here.
                // For example:
                // DevMenuItem(
                //     title = "Log Viewer",
                //     screen = LogViewerScreen
                // )
            )
        }.value

        return DevMenuMainScreen.UiState.Content(
            menuItems = menuItems,
            onEvent = { event ->
                when (event) {
                    is DevMenuMainScreen.UiEvent.OnItemClick -> {
                        navigator.goTo(event.screen)
                    }
                    DevMenuMainScreen.UiEvent.OnBack -> {
                        navigator.pop()
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