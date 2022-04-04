package io.projectnewm.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.projectnewm.shared.login.LogInRepository
import io.projectnewm.shared.login.LogInService
import io.projectnewm.shared.login.LogInServiceImpl
import io.projectnewm.shared.login.LoginUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Singleton
    @Provides
    fun provideLoginUseCase(
        repository: LogInRepository,
    ): LoginUseCase {
        return LoginUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(
        service: LogInService,
    ): LogInRepository {
        return LogInRepository(service = service)
    }

    @Singleton
    @Provides
    fun provideLoginService(
        httpClient: HttpClient,
        service: LogInService,
    ): LogInService {
        return LogInServiceImpl(httpClient = httpClient)
    }
}
