package com.projectnewm.example.di

import com.projectnewm.KtorClientFactory
import com.projectnewm.example.ExampleService
import com.projectnewm.example.ExampleServiceImpl

class ExampleNetworkModule {
    val exampleService: ExampleService by lazy {
        ExampleServiceImpl(
            httpClient = KtorClientFactory().build(),
            baseUrl = ExampleServiceImpl.BASE_URL
        )
    }
}