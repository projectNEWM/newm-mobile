package io.projectnewm.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.projectnewm.shared.example.ExampleRepository
import io.projectnewm.shared.example.ExampleService
import io.projectnewm.shared.example.ExampleServiceImpl
import io.projectnewm.shared.example.ExampleUseCase
import io.projectnewm.shared.repository.db.ExampleDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExampleModule {

    @Singleton
    @Provides
    fun provideExampleUseCase(
        exampleRepository: ExampleRepository,
    ): ExampleUseCase {
        return ExampleUseCase(exampleRepository)
    }

    @Singleton
    @Provides
    fun provideExampleRepository(
        exampleService: ExampleService,
        exampleDao: ExampleDao
    ): ExampleRepository {
        return ExampleRepository(exampleService, exampleDao)
    }

    @Singleton
    @Provides
    fun provideExampleService(
        httpClient: HttpClient,
    ): ExampleService {
        return ExampleServiceImpl(httpClient = httpClient)
    }
}