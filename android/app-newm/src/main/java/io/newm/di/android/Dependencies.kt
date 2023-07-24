package io.newm.di.android

import io.newm.Logout
import io.newm.RestartApp
import io.newm.screens.home.categories.MusicalCategoriesViewModel
import io.newm.feature.login.screen.createaccount.CreateAccountViewModel
import io.newm.screens.profile.ProfileViewModel
import io.newm.feature.login.screen.LoginViewModel
import io.newm.feature.login.screen.createaccount.CreateAccountScreenPresenter
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModule = module {
    viewModelOf(::MusicalCategoriesViewModel)
    viewModelOf(::CreateAccountViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ProfileViewModel)
    factory { params -> CreateAccountScreenPresenter(params.get(), get()) }
}

val androidModules = module {
    single { Logout(get(), get()) }
    single { RestartApp(get()) }
}
