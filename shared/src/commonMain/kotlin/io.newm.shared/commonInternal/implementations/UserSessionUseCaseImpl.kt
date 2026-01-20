package io.newm.shared.commonInternal.implementations

import io.newm.shared.commonInternal.TokenManager
import io.newm.shared.commonPublic.usecases.UserSessionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

internal class UserSessionUseCaseImpl(
    private val tokenManager: TokenManager,
) : KoinComponent,
    UserSessionUseCase {
    override suspend fun isLoggedIn(): Boolean = tokenManager.getAccessToken()?.isEmpty()?.not() == true

    override fun isLoggedInFlow(): Flow<Boolean> =
        flow {
            emit(tokenManager.getAccessToken()?.isNotEmpty() == true)
        }
}
