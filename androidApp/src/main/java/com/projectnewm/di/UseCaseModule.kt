package com.projectnewm.di

import com.projectnewm.example.ExampleRepository
import com.projectnewm.example.ExampleService
import com.projectnewm.example.ExampleUseCase
import com.projectnewm.repository.db.ExampleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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