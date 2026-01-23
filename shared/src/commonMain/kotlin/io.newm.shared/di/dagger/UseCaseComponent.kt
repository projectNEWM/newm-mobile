package io.newm.shared.di.dagger

import io.newm.shared.commonInternal.implementations.LoginUseCaseImpl
import io.newm.shared.commonInternal.implementations.ResetPasswordUseCaseImpl
import io.newm.shared.commonInternal.implementations.SignupUseCaseImpl
import io.newm.shared.commonPublic.usecases.LoginUseCase
import io.newm.shared.commonPublic.usecases.ResetPasswordUseCase
import io.newm.shared.commonPublic.usecases.SignupUseCase
import me.tatarka.inject.annotations.Provides

interface UseCaseComponent {
    @Provides fun provideLoginUseCase(impl: LoginUseCaseImpl): LoginUseCase = impl

    @Provides fun provideSignupUseCase(impl: SignupUseCaseImpl): SignupUseCase = impl

    @Provides
    fun provideResetPasswordUseCase(impl: ResetPasswordUseCaseImpl): ResetPasswordUseCase = impl
}
