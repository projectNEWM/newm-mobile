package io.newm.shared.public.usecases

import io.newm.shared.public.models.error.KMMException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

interface ChangePasswordUseCase {
    @Throws(KMMException::class, CancellationException::class)
    suspend fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String)
}

class ChangePasswordUseCaseProvider(): KoinComponent {
    private val changePasswordUseCase: ChangePasswordUseCase by inject()

    fun get(): ChangePasswordUseCase {
        return this.changePasswordUseCase
    }
}