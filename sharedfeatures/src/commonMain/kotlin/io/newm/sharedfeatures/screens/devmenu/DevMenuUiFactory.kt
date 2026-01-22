package io.newm.sharedfeatures.screens.devmenu

import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import io.newm.sharedfeatures.screens.DevMenuMainScreen
import io.newm.sharedfeatures.screens.FeatureFlagsListScreen
import io.newm.sharedfeatures.screens.devmenu.featureflaglist.FeatureFlagsListUi
import me.tatarka.inject.annotations.Inject

class DevMenuUiFactory
    @Inject
    constructor() : Ui.Factory {
        override fun create(
            screen: Screen,
            context: CircuitContext,
        ): Ui<*>? =
            when (screen) {
                DevMenuMainScreen -> {
                    ui<DevMenuMainScreen.UiState> { state, modifier -> DevMenuUi(state, modifier) }
                }

                is FeatureFlagsListScreen -> {
                    ui<FeatureFlagsListScreen.UiState> { state, modifier ->
                        FeatureFlagsListUi(state, modifier)
                    }
                }

                else -> {
                    null
                }
            }
    }
