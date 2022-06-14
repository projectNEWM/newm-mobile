package io.projectnewm.di

import io.projectnewm.shared.KtorClientFactory
import org.koin.dsl.module

val networkModule = module {
    single { KtorClientFactory().build() }
}

