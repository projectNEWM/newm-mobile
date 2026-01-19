package io.newm.sharedfeatures.devmenu

import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.shared.di.dagger.ActivityScope
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface DevMenuComponent {
    @Provides
    @IntoSet
    @ActivityScope
    fun devMenuPresenterFactory(
        config: NewmSharedBuildConfig,
        factory: DevMenuPresenterFactory
    ): Presenter.Factory = if (config.isDebug) factory else object : Presenter.Factory {
        override fun create(screen: Screen, navigator: Navigator, context: CircuitContext): Presenter<*>? = null
    }

    @Provides
    @IntoSet
    @ActivityScope
    fun devMenuUiFactory(
        config: NewmSharedBuildConfig,
        factory: DevMenuUiFactory
    ): Ui.Factory = if (config.isDebug) factory else object : Ui.Factory {
        override fun create(screen: Screen, context: CircuitContext): Ui<*>? = null
    }
}