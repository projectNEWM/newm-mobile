package io.projectnewm.shared.di

import io.projectnewm.shared.KtorClientFactory
import io.projectnewm.shared.example.ExampleService
import io.projectnewm.shared.example.ExampleServiceImpl

class ExampleNetworkModule {
    val exampleService: ExampleService by lazy {
        ExampleServiceImpl(
            httpClient = KtorClientFactory().build(),
            baseUrl = ExampleServiceImpl.BASE_URL
        )
    }
}