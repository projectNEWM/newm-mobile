package io.newm.shared.login

import io.newm.shared.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface UserSession {
    fun userLoginStateValue(): Boolean

    fun userLoginState(): Flow<Boolean>

    fun logout()
}

internal class UserSessionImpl : KoinComponent, UserSession {
    private val _mutableUserLoginState = MutableStateFlow(false)

    private val tokenManager: TokenManager by inject()

    init {
        _mutableUserLoginState.value = userLoginStateValue()
    }

    override fun userLoginStateValue(): Boolean {
        return tokenManager.getAccessToken().isNullOrEmpty().not()
    }

    override fun userLoginState(): Flow<Boolean> {
        return _mutableUserLoginState
    }

    override fun logout() {
        tokenManager.clearToken()
    }
}