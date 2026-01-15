package io.newm.sharedfeatures.login

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import io.newm.shared.di.dagger.ActivityScope
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface LoginComponent {
    @Provides
    @IntoSet
    @ActivityScope
    fun LoginPresenterFactory.bind(): Presenter.Factory = this

    @Provides
    @IntoSet
    @ActivityScope
    fun LoginUiFactory.bind(): Ui.Factory = this

    @Provides
    @ActivityScope
    fun RecaptchaManagerImpl.bind(): RecaptchaManager = this
}
