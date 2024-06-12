package io.newm.shared.internal


import co.touchlab.kermit.Logger
import io.newm.shared.internal.db.PreferencesDataStore

internal class TokenManager(private val storage: PreferencesDataStore) {
    private val logger = Logger.withTag("NewmKMM-TokenManagerImpl")

    fun getAccessToken(): String? {
        return storage.getString(ACCESS_TOKEN_KEY) ?: run {
            logger.d("No Access Token found - Time to Login")
            null
        }
    }

    fun getRefreshToken(): String? {
        val refreshToken = storage.getString(REFRESH_TOKEN_KEY)
        if (refreshToken.isNullOrEmpty()) {
            logger.d("No Refresh Token found - Time to Login")
            return null
        } else {
            return refreshToken
        }
    }

    fun clearToken() {
        storage.deleteValue(ACCESS_TOKEN_KEY)
        storage.deleteValue(REFRESH_TOKEN_KEY)
    }

    fun setAuthTokens(accessToken: String, refreshToken: String) {
        storage.saveString(ACCESS_TOKEN_KEY, accessToken)
        storage.saveString(REFRESH_TOKEN_KEY, refreshToken)
    }

    companion object {
        private const val REFRESH_TOKEN_KEY = "refreshToken"
        const val ACCESS_TOKEN_KEY = "accessToken"
    }
}