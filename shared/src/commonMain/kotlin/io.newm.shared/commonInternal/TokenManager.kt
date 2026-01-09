package io.newm.shared.commonInternal

/**
 * Token manager interface for handling authentication tokens.
 * All operations are suspend functions to support async storage implementations.
 */
interface TokenManager {

    suspend fun getAccessToken(): String?

    suspend fun getRefreshToken(): String?

    suspend fun clearToken()

    suspend fun setAuthTokens(accessToken: String, refreshToken: String)

}