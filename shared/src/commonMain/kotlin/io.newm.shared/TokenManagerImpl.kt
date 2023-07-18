package io.newm.shared

import co.touchlab.kermit.Logger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import shared.SecureStorage

internal class TokenManagerImpl() : KoinComponent, TokenManager {
    private val storage: SecureStorage by inject()
    private val logger = Logger.withTag("NewmKMM-TokenManagerImpl")

    override fun hasTokens(): Boolean {
        return getAccessToken() != null && getRefreshToken() != null
    }

    override fun getAccessToken(): String? {
        return storage.retrieve(ACCESS_TOKEN_KEY) ?: run {
            logger.d("No Access Token found - Time to Login")
            null
        }
    }

    override fun getRefreshToken(): String? {
        return storage.retrieve(REFRESH_TOKEN_KEY) ?: run {
            logger.d("No Refresh Token found - Time to Login")
            null
        }
    }

    override fun clearToken() {
        storage.remove(ACCESS_TOKEN_KEY)
        storage.remove(REFRESH_TOKEN_KEY)
    }

    override fun setAuthTokens(accessToken: String, refreshToken: String) {
        storage.store(ACCESS_TOKEN_KEY, accessToken)
        storage.store(REFRESH_TOKEN_KEY, refreshToken)
    }

    companion object {
        private const val REFRESH_TOKEN_KEY = "refreshToken"
        const val ACCESS_TOKEN_KEY = "accessToken"
    }
}