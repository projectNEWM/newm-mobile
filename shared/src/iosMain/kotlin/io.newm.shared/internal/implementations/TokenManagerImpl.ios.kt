package io.newm.shared.internal.implementations

import io.newm.shared.NewmAppLogger
import io.newm.shared.commonInternal.TokenManager
import io.newm.shared.commonInternal.db.PreferencesDataStore

internal class TokenManagerImpl(
    private val storage: PreferencesDataStore,
    private val logger: NewmAppLogger,
) : TokenManager {
    override suspend fun getAccessToken(): String? =
        storage.getString(ACCESS_TOKEN_KEY)
            ?: run {
                logger.debug("TokenManagerImpl", "No Access Token found - Time to Login")
                null
            }

    override suspend fun getRefreshToken(): String? {
        val refreshToken = storage.getString(REFRESH_TOKEN_KEY)
        if (refreshToken.isNullOrEmpty()) {
            logger.debug("TokenManagerImpl", "No Refresh Token found - Time to Login")
            return null
        } else {
            return refreshToken
        }
    }

    override suspend fun clearToken() {
        storage.deleteValue(ACCESS_TOKEN_KEY)
        storage.deleteValue(REFRESH_TOKEN_KEY)
    }

    override suspend fun setAuthTokens(
        accessToken: String,
        refreshToken: String,
    ) {
        storage.saveString(ACCESS_TOKEN_KEY, accessToken)
        storage.saveString(REFRESH_TOKEN_KEY, refreshToken)
    }

    companion object {
        private const val REFRESH_TOKEN_KEY = "refreshToken"
        private const val ACCESS_TOKEN_KEY = "accessToken"
    }
}
