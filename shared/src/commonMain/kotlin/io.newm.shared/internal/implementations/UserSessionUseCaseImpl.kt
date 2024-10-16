package io.newm.shared.internal.implementations

import io.newm.shared.internal.TokenManager
import io.newm.shared.public.usecases.UserSessionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.startWith
import org.koin.core.component.KoinComponent

internal class UserSessionUseCaseImpl(
    private val tokenManager: TokenManager
    ) : KoinComponent, UserSessionUseCase {

    override fun isLoggedIn(): Boolean {
        return tokenManager.getAccessToken()?.isEmpty()?.not() == true
    }

    override fun isLoggedInFlow(): Flow<Boolean> {
        return flow {
            tokenManager.getAccessToken()?.isNotEmpty()
        }
    }
}