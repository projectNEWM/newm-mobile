package io.newm.sharedfeatures.di

import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import io.newm.shared.di.dagger.ActivityScope
import io.newm.sharedfeatures.screens.auth.login.LoginComponent
import io.newm.sharedfeatures.screens.auth.resetpassword.ResetPasswordComponent
import io.newm.sharedfeatures.screens.auth.signup.CreateAccountComponent
import io.newm.sharedfeatures.screens.auth.welcome.WelcomeComponent
import io.newm.sharedfeatures.screens.devmenu.DevMenuComponent
import me.tatarka.inject.annotations.Provides

interface CircuitComponent :
    WelcomeComponent,
    DevMenuComponent,
    LoginComponent,
    CreateAccountComponent,
    ResetPasswordComponent {
    val circuit: Circuit

    @Provides
    @ActivityScope
    fun provideCircuit(
        uiFactories: Set<Ui.Factory>,
        presenterFactories: Set<Presenter.Factory>,
    ): Circuit =
        Circuit
            .Builder()
            .addUiFactories(uiFactories)
            .addPresenterFactories(presenterFactories)
            .build()
}
