package io.newm.sharedfeatures

import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import io.newm.sharedfeatures.welcome.WelcomeComponent
import me.tatarka.inject.annotations.Provides

interface CircuitComponent : WelcomeComponent {
    val circuit: Circuit

    @Provides
    @ActivityScope
    fun provideCircuit(
        uiFactories: Set<Ui.Factory>,
        presenterFactories: Set<Presenter.Factory>,
    ): Circuit = Circuit.Builder()
        .addUiFactories(uiFactories)
        .addPresenterFactories(presenterFactories)
        .build()
}