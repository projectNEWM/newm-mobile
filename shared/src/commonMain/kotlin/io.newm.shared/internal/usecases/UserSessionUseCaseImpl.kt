package io.newm.shared.internal.usecases

import io.newm.shared.internal.TokenManager
import io.newm.shared.public.usecases.UserSessionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

internal class UserSessionUseCaseImpl(
    private val tokenManager: TokenManager
    ) : KoinComponent, UserSessionUseCase {

    override fun isLoggedIn(): Boolean {
        return tokenManager.getAccessToken().isNullOrEmpty().not()
    }

    override fun isLoggedInFlow(): Flow<Boolean> {
        return flow {
            tokenManager.getAccessToken().isNullOrEmpty().not()
        }
    }
}