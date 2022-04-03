package io.projectnewm.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.projectnewm.shared.example.ExampleRepository
import io.projectnewm.shared.example.ExampleService
import io.projectnewm.shared.example.ExampleUseCase
import io.projectnewm.shared.login.LoginRepository
import io.projectnewm.shared.login.LoginService
import io.projectnewm.shared.login.LoginServiceImpl
import io.projectnewm.shared.login.LoginUseCase
import io.projectnewm.shared.repository.db.ExampleDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Singleton
    @Provides
    fun provideLoginUseCase(
        repository: LoginRepository,
    ): LoginUseCase {
        return LoginUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(
        service: LoginService,
    ): LoginRepository {
        return LoginRepository(service = service)
    }

    @Singleton
    @Provides
    fun provideLoginService(
        httpClient: HttpClient,
        service: LoginService,
    ): LoginService {
        return LoginServiceImpl(httpClient = httpClient)
    }
}
