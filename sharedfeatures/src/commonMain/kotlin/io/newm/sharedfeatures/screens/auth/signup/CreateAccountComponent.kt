package io.newm.sharedfeatures.screens.auth.signup

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import io.newm.shared.di.dagger.ActivityScope
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface CreateAccountComponent {
    @Provides
    @IntoSet
    @ActivityScope
    fun CreateAccountPresenterFactory.bind(): Presenter.Factory = this

    @Provides @IntoSet @ActivityScope
    fun CreateAccountUiFactory.bind(): Ui.Factory = this
}
