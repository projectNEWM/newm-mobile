package io.projectnewm.di.viewmodels

import io.projectnewm.screens.home.categories.MusicalCategoriesViewModel
import io.projectnewm.feature.login.screen.SignupViewModel
import io.projectnewm.feature.login.screen.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModule = module {
    viewModelOf(::MusicalCategoriesViewModel)
    //TODO: Separate viewmodel models per feature instead of wiring them manually
    viewModelOf(::SignupViewModel)
    viewModelOf(::LoginViewModel)
}
