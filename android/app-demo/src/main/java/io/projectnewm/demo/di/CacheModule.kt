package io.projectnewm.demo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.projectnewm.demo.DemoApplication
import io.projectnewm.shared.repository.db.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideNewmDatabase(context: DemoApplication): NewmDb {
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