package io.projectnewm.di.viewmodels

import io.projectnewm.screens.home.categories.MusicalCategoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModule = module {
    viewModelOf(::MusicalCategoriesViewModel)
}
