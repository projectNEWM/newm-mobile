package io.newm.shared.commonInternal

interface TokenProvider {
    suspend fun getAccessToken(): String?

    suspend fun getRefreshToken(): String?
}
