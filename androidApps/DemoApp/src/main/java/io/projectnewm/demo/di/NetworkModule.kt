package io.projectnewm.demo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.projectnewm.KtorClientFactory
import io.projectnewm.example.ExampleService
import io.projectnewm.example.ExampleServiceImpl
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