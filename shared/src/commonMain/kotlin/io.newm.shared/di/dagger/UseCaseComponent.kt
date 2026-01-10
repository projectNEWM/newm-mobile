package io.newm.shared.di.dagger

import io.newm.shared.commonInternal.implementations.LoginUseCaseImpl
import io.newm.shared.commonPublic.usecases.LoginUseCase
import me.tatarka.inject.annotations.Provides

interface UseCaseComponent {
    @Provides
    fun provideLoginUseCase(impl: LoginUseCaseImpl): LoginUseCase = impl
}
