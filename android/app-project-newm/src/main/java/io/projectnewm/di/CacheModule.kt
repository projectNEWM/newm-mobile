package io.projectnewm.di

import io.projectnewm.shared.repository.db.*
import org.koin.dsl.module

val cacheModule = module {
    single { NewmDatabaseFactory(driverFactory = SqlDelightDriverFactory(get())).createDatabase() }
    single<ExampleDao> { ExampleDaoImpl(get()) }
}