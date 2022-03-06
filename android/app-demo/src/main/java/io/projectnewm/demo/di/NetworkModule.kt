package io.projectnewm.demo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.projectnewm.shared.KtorClientFactory
import io.projectnewm.shared.example.ExampleService
import io.projectnewm.shared.example.ExampleServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return KtorClientFactory()
            .build()
    }

    @Singleton
    @Provides
    fun provideExampleService(
        httpClient: HttpClient,
    ): ExampleService {
        return ExampleServiceImpl(httpClient = httpClient)
    }
}