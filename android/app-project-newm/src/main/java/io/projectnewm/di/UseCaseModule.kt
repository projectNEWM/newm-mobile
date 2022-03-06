package io.projectnewm.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.projectnewm.shared.example.ExampleRepository
import io.projectnewm.shared.example.ExampleService
import io.projectnewm.shared.example.ExampleUseCase
import io.projectnewm.shared.repository.db.ExampleDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

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
}