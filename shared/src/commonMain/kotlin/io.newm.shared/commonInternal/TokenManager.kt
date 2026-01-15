package io.newm.shared.commonInternal

/**
 * Token manager interface for handling authentication tokens.
 * All operations are suspend functions to support async storage implementations.
 */
interface TokenManager : TokenProvider {

    override suspend fun getAccessToken(): String?

    override suspend fun getRefreshToken(): String?

    suspend fun clearToken()

    suspend fun setAuthTokens(accessToken: String, refreshToken: String)

}
