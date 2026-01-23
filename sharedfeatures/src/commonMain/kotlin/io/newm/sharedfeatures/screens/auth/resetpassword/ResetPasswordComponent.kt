package io.newm.sharedfeatures.screens.auth.resetpassword

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import io.newm.shared.di.dagger.ActivityScope
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface ResetPasswordComponent {
    @Provides
    @IntoSet
    @ActivityScope
    fun ResetPasswordPresenterFactory.bind(): Presenter.Factory = this

    @Provides @IntoSet @ActivityScope
    fun ResetPasswordUiFactory.bind(): Ui.Factory = this
}
