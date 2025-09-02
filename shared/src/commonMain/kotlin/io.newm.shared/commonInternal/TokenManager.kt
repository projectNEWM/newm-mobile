package io.newm.shared.commonInternal

interface TokenManager {

    fun getAccessToken(): String?

    fun getRefreshToken(): String?

    fun clearToken()

    fun setAuthTokens(accessToken: String, refreshToken: String)

}