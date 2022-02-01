package com.projectnewm.di

import com.projectnewm.NewmApplication
import com.projectnewm.repository.db.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideNewmDatabase(context: NewmApplication): NewmDb {
        return NewmDatabaseFactory(driverFactory = SqlDelightDriverFactory(context)).createDatabase()
    }

    @Singleton
    @Provides
    fun provideDao(
        newmDatabase: NewmDb
    ): ExampleDao {
        return ExampleDaoImpl(db = newmDatabase)
    }
}