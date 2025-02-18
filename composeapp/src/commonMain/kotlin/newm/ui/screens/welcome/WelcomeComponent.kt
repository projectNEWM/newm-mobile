package newm.ui.screens.welcome

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import newm.inject.ActivityScope
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface WelcomeComponent {
    @Provides
    @IntoSet
    @ActivityScope
    fun WelcomePresenterFactory.bind(): Presenter.Factory = this


    @Provides
    @IntoSet
    @ActivityScope
    fun WelcomeUiFactory.bind(): Ui.Factory = this
}
