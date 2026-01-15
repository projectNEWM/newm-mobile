package io.newm.sharedfeatures.devmenu

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import io.newm.shared.di.dagger.ActivityScope
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface DevMenuComponent {
    @Provides
    @IntoSet
    @ActivityScope
    fun DevMenuPresenterFactory.bind() : Presenter.Factory = this

    @Provides
    @IntoSet
    @ActivityScope
    fun DevMenuUiFactory.bind(): Ui.Factory = this

    @Provides
    @IntoSet
    @ActivityScope
    fun devMenuPresenterFactory(factory: DevMenuPresenterFactory): Presenter.Factory = factory

    @Provides
    @IntoSet
    @ActivityScope
    fun devMenuUiFactory(factory: DevMenuUiFactory): Ui.Factory = factory
}