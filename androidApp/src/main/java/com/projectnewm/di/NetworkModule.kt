package com.projectnewm.di

import com.projectnewm.KtorClientFactory
import com.projectnewm.example.ExampleService
import com.projectnewm.example.ExampleServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return KtorClientFactory().build()
    }

    @Singleton
    @Provides
    fun provideExampleService(
        httpClient: HttpClient,
    ): ExampleService {
        return ExampleServiceImpl(httpClient = httpClient)
    }
}