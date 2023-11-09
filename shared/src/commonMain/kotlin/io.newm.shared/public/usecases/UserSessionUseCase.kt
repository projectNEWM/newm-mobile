package io.newm.shared.public.usecases

import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface UserSessionUseCase {
    fun userLoginStateValue(): Boolean

    fun userLoginState(): Flow<Boolean>

    fun logout()
}

class UserSessionUseCaseProvider(): KoinComponent {
    private val userSessionUseCase: UserSessionUseCase by inject()

    fun get(): UserSessionUseCase {
        return this.userSessionUseCase
    }
}