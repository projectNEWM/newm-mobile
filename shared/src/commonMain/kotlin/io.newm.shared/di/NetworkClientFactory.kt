package io.newm.shared.di

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import io.newm.shared.internal.HttpRoutes
import io.newm.shared.internal.TokenManager
import io.newm.shared.internal.repositories.LogInRepository
import io.newm.shared.internal.api.models.LoginResponse
import io.newm.shared.public.models.error.KMMException
import kotlinx.serialization.json.Json

internal class NetworkClientFactory(
    private val httpClientEngine: HttpClientEngine,
    private val json: Json,
    private val repository: LogInRepository,
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
            this.expectSuccess = true
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
            this.expectSuccess = true
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
                                repository.logout()
                                logger.d { "NewmKMM - refreshTokens Invalid Token response: $renewTokens" }
                                throw KMMException("Invalid Token response")
                            }
                        } catch (e: Exception) {
                            repository.logout()
                            logger.d { "NewmKMM - refreshTokens: Exception: $e" }
                            throw KMMException("Refresh token failed: $e")
                        }
                    }
                }
            }
            install(refreshTokenPlugin)
        }
    }
}