package io.newm.sharedfeatures.devmenu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import io.newm.sharedfeatures.screens.DevMenuItem
import io.newm.sharedfeatures.screens.DevMenuMainScreen
import io.newm.sharedfeatures.screens.DevMenuMainScreen.UiEvent
import io.newm.sharedfeatures.screens.FeatureFlagsListScreen
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

class DevMenuPresenter
    @Inject
    constructor(
        @Assisted private val navigator: Navigator,
    ) : Presenter<DevMenuMainScreen.UiState> {
        val menuItems =
            listOf(
                DevMenuItem(
                    title = "Feature Flags",
                    screen = FeatureFlagsListScreen,
                    description = "Manage and test feature flags",
                ),
            )

        @Composable
        override fun present(): DevMenuMainScreen.UiState =
            DevMenuMainScreen.UiState.Content(
                menuItems = menuItems,
                onEvent = { event ->
                    when (event) {
                        is UiEvent.OnItemClick -> navigator.goTo(event.screen)
                        UiEvent.OnBack -> navigator.pop()
                    }
                },
            )
    }

class DevMenuPresenterFactory
    @Inject
    constructor(
        private val presenter: (Navigator) -> DevMenuPresenter,
        private val featureFlagsListPresenter: (Navigator) -> FeatureFlagsListPresenter,
    ) : Presenter.Factory {
        override fun create(
            screen: Screen,
            navigator: Navigator,
            context: CircuitContext,
        ): Presenter<*>? =
            when (screen) {
                is DevMenuMainScreen -> presenter(navigator)
                is FeatureFlagsListScreen -> featureFlagsListPresenter(navigator)
                else -> null
            }
    }
