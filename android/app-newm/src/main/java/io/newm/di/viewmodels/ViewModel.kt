package io.newm.di.viewmodels

import io.newm.screens.home.categories.MusicalCategoriesViewModel
import io.newm.feature.login.screen.CreateAccountViewModel
import io.newm.feature.login.screen.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModule = module {
    viewModelOf(::MusicalCategoriesViewModel)
    //TODO: Separate viewmodel models per feature instead of wiring them manually
    viewModelOf(::CreateAccountViewModel)
    viewModelOf(::LoginViewModel)
}
