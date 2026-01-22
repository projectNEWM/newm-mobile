package io.newm.sharedfeatures.screens.devmenu

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
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
