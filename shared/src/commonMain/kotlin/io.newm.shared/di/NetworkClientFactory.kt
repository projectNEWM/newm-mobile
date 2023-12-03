package io.newm.shared.di

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.api.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.newm.shared.internal.HttpRoutes
import io.newm.shared.internal.TokenManager
import io.newm.shared.login.models.LoginResponse
import io.newm.shared.public.usecases.UserSessionUseCase
import kotlinx.serialization.json.Json

internal class NetworkClientFactory(
    private val httpClientEngine: HttpClientEngine,
    private val json: Json,
    private val tokenManager: TokenManager,
    private val enableNetworkLogs: Boolean,
) {
    private val logger = co.touchlab.kermit.Logger.withTag("NewmKMM-NetworkClientFactory")

    private lateinit var _authHttpClient: HttpClient
    private lateinit var _httpClient: HttpClient

    fun authHttpClient(): HttpClient {
        if (!::_authHttpClient.isInitialized) {
            _authHttpClient = createAuthHttpClient()
        }
        return _authHttpClient
    }

    fun httpClient(): HttpClient {
        if (!::_httpClient.isInitialized) {
            _httpClient = createHttpClient()
        }
        return _httpClient
    }

    private fun createHttpClient(): HttpClient {
        return HttpClient(httpClientEngine) {
            defaultRequest {
                url(HttpRoutes.getHost())
            }
            install(ContentNegotiation) {
                json(json)
            }
            if (enableNetworkLogs) {
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.ALL
                }
            }
        }
    }

    private val refreshTokenPlugin = createClientPlugin("RefreshTokenPlugin") {
        onRequest { request, _ ->
            if(request.url.encodedPath == "/v1/auth/refresh") {
                request.headers.remove(HttpHeaders.Authorization)
                request.headers.append(HttpHeaders.Authorization, "Bearer ${tokenManager.getRefreshToken()}")
            }
        }
    }

    private fun createAuthHttpClient(): HttpClient {
        logger.d { "NewmKMM - createAuthHttpClient" }
        return HttpClient(httpClientEngine) {
            defaultRequest {
                url(HttpRoutes.getHost())
            }

            install(ContentNegotiation) {
                json(json)
            }
            if (enableNetworkLogs) {
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.ALL
                }
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        logger.d { "KMM - loadTokens" }
                        BearerTokens(
                            accessToken = tokenManager.getAccessToken().orEmpty(),
                            refreshToken = tokenManager.getRefreshToken().orEmpty()
                        )
                    }


                    refreshTokens {
                        try {
                            val renewTokens = client.get( "/v1/auth/refresh") {
                                markAsRefreshTokenRequest()
                            }.body<LoginResponse>()
                            if(renewTokens.accessToken != null && renewTokens.refreshToken != null) {
                                tokenManager.setAuthTokens(
                                    renewTokens.accessToken,
                                    renewTokens.refreshToken
                                )
                                BearerTokens(
                                    accessToken = tokenManager.getAccessToken()!!,
                                    refreshToken = tokenManager.getRefreshToken()!!
                                )
                            } else {
                                logger.d { "NewmKMM - refreshTokens Invalid Token response: $renewTokens" }
                                throw Exception("Invalid Token response")
                            }
                        } catch (e: Exception) {
                            logger.d { "NewmKMM - refreshTokens: Exception: $e" }
                            null
                        }
                    }
                }
            }
            install(refreshTokenPlugin)
        }
    }
}
