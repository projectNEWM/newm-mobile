package io.newm.shared

interface TokenManager {
    fun hasTokens(): Boolean
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun clearToken()
    fun setAuthTokens(accessToken: String, refreshToken: String)
}

