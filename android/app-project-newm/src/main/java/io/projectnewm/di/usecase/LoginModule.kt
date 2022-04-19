package io.projectnewm.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.projectnewm.shared.login.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Singleton
    @Provides
    fun provideLoginUseCase(): LoginUseCase {
        return LoginConfig.getLoginUseCase()
    }

}
