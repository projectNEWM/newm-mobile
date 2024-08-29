package io.newm.shared.internal.implementations


import io.newm.shared.NewmAppLogger
import io.newm.shared.internal.TokenManager
import io.newm.shared.internal.db.PreferencesDataStore

internal class TokenManagerImpl(
    private val storage: PreferencesDataStore,
    private val logger: NewmAppLogger
) : TokenManager {

    override fun getAccessToken(): String? {
        return storage.getString(ACCESS_TOKEN_KEY) ?: run {
            logger.debug("TokenManagerImpl", "No Access Token found - Time to Login")
            null
        }
    }

    override fun getRefreshToken(): String? {
        val refreshToken = storage.getString(REFRESH_TOKEN_KEY)
        if (refreshToken.isNullOrEmpty()) {
            logger.debug("TokenManagerImpl", "No Refresh Token found - Time to Login")
            return null
        } else {
            return refreshToken
        }
    }

    override fun clearToken() {
        storage.deleteValue(ACCESS_TOKEN_KEY)
        storage.deleteValue(REFRESH_TOKEN_KEY)
    }

    override fun setAuthTokens(accessToken: String, refreshToken: String) {
        storage.saveString(ACCESS_TOKEN_KEY, accessToken)
        storage.saveString(REFRESH_TOKEN_KEY, refreshToken)
    }

    companion object {
        private const val REFRESH_TOKEN_KEY = "refreshToken"
        private const val ACCESS_TOKEN_KEY = "accessToken"
    }
}