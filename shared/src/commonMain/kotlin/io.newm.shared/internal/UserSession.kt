package io.newm.shared.internal

import io.newm.shared.internal.repositories.ConnectWalletManager
import io.newm.shared.public.usecases.UserSessionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import shared.Notification
import shared.postNotification


internal class UserSessionUseCaseImpl : KoinComponent, UserSessionUseCase {
    private val _mutableUserLoginState = MutableStateFlow(false)

    private val tokenManager: TokenManager by inject()
    private val connectWalletManager: ConnectWalletManager by inject()

    init {
        _mutableUserLoginState.value = isLoggedIn()
    }

    override fun isLoggedIn(): Boolean {
        return tokenManager.getAccessToken().isNullOrEmpty().not()
    }

    override fun isLoggedInFlow(): Flow<Boolean> {
        return _mutableUserLoginState
    }

    override fun logout() {
        tokenManager.clearToken()
        connectWalletManager.disconnect()
        postNotification(Notification.loginStateChanged)
    }
}