package io.projectnewm.example.di

import io.projectnewm.KtorClientFactory
import io.projectnewm.example.ExampleService
import io.projectnewm.example.ExampleServiceImpl

class ExampleNetworkModule {
    val exampleService: ExampleService by lazy {
        ExampleServiceImpl(
            httpClient = KtorClientFactory().build(),
            baseUrl = ExampleServiceImpl.BASE_URL
        )
    }
}