package io.newm.sharedfeatures.screens.devmenu

import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import io.newm.sharedfeatures.screens.DevMenuMainScreen
import io.newm.sharedfeatures.screens.FeatureFlagsListScreen
import io.newm.sharedfeatures.screens.devmenu.featureflaglist.FeatureFlagsListPresenter
import me.tatarka.inject.annotations.Inject

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
