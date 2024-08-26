package io.newm.shared.internal.implementations


import co.touchlab.kermit.Logger
import io.newm.shared.internal.TokenManager
import io.newm.shared.internal.db.PreferencesDataStore

internal class TokenManagerImpl(private val storage: PreferencesDataStore) : TokenManager {
    private val logger = Logger.withTag("NewmKMM-TokenManagerImpl")

    override fun getAccessToken(): String? {
        return storage.getString(ACCESS_TOKEN_KEY) ?: run {
            logger.d("No Access Token found - Time to Login")
            null
        }
    }

    override fun getRefreshToken(): String? {
        val refreshToken = storage.getString(REFRESH_TOKEN_KEY)
        if (refreshToken.isNullOrEmpty()) {
            logger.d("No Refresh Token found - Time to Login")
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