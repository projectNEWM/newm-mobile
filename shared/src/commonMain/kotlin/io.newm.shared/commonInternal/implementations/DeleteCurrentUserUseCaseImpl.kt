package io.newm.shared.commonInternal.implementations

import io.newm.shared.commonInternal.repositories.UserRepository
import io.newm.shared.commonPublic.usecases.DeleteCurrentUserUseCase
import io.newm.shared.commonPublic.usecases.LoginUseCase
import org.koin.core.component.KoinComponent

internal class DeleteCurrentUserUseCaseImpl(
    private val userRepository: UserRepository,
    private val logoutUseCase: LoginUseCase
): DeleteCurrentUserUseCase, KoinComponent {
    override suspend fun delete() {
        userRepository.deleteCurrentUser()
        logoutUseCase.logout()
    }
}