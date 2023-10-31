package io.newm.shared


import co.touchlab.kermit.Logger
import com.liftric.kvault.KVault
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class TokenManager : KoinComponent {
    private val storage: KVault by inject()
    private val logger = Logger.withTag("NewmKMM-TokenManagerImpl")

    fun hasTokens(): Boolean {
        return getAccessToken() != null && getRefreshToken() != null
    }

    fun getAccessToken(): String? {
        return storage.string(ACCESS_TOKEN_KEY) ?: run {
            logger.d("No Access Token found - Time to Login")
            null
        }
    }

    fun getRefreshToken(): String? {
        return storage.string(REFRESH_TOKEN_KEY) ?: run {
            logger.d("No Refresh Token found - Time to Login")
            null
        }
    }

    fun clearToken() {
        storage.deleteObject(ACCESS_TOKEN_KEY)
        storage.deleteObject(REFRESH_TOKEN_KEY)
    }

    fun setAuthTokens(accessToken: String, refreshToken: String) {
        storage.set(ACCESS_TOKEN_KEY, accessToken)
        storage.set(REFRESH_TOKEN_KEY, refreshToken)
    }

    companion object {
        private const val REFRESH_TOKEN_KEY = "refreshToken"
        const val ACCESS_TOKEN_KEY = "accessToken"
    }
}