package io.projectnewm.demo.di

import io.projectnewm.shared.KtorClientFactory
import io.projectnewm.shared.example.ExampleService
import io.projectnewm.shared.example.ExampleServiceImpl
import org.koin.dsl.module

val networkModule = module {
    single { KtorClientFactory().build() }
    single<ExampleService> { ExampleServiceImpl(get()) }
}
