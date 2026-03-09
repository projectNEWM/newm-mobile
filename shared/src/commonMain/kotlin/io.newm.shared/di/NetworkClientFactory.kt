package io.newm.shared.di

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonInternal.SessionManager
import io.newm.shared.commonInternal.TokenManager
import io.newm.shared.commonInternal.api.models.LoginResponse
import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.shared.di.dagger.ApplicationScope
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject

@ApplicationScope
@Inject
class NetworkClientFactory(
    private val httpClientEngine: HttpClientEngine,
    private val json: Json,
    private val tokenManager: TokenManager,
    private val sessionManager: SessionManager,
    private val enableNetworkLogs: Boolean,
    private val buildConfig: NewmSharedBuildConfig,
    private val appLogger: NewmAppLogger,
) {
    private lateinit var authHttpClientInternal: HttpClient
    private lateinit var httpClientInternal: HttpClient

    fun authHttpClient(): HttpClient {
        if (!::authHttpClientInternal.isInitialized) {
            authHttpClientInternal = createAuthHttpClient()
        }
        return authHttpClientInternal
    }

    fun httpClient(): HttpClient {
        if (!::httpClientInternal.isInitialized) {
            httpClientInternal = createHttpClient()
        }
        return httpClientInternal
    }

    private fun createHttpClient(): HttpClient =
        HttpClient(httpClientEngine) {
            this.expectSuccess = true
            defaultRequest { url(buildConfig.baseUrl) }
            install(ContentNegotiation) { json(json) }
            install(ContentEncoding)
            if (enableNetworkLogs) {
                install(Logging) {
                    logger = Logger.SIMPLE
                    level = LogLevel.ALL
                }
            }
        }

    private val refreshTokenPlugin =
        createClientPlugin("RefreshTokenPlugin") {
            onRequest { request, _ ->
                if (request.url.encodedPath == "/v1/auth/refresh") {
                    request.headers.remove(HttpHeaders.Authorization)
                    request.bearerAuth(tokenManager.getRefreshToken().toString())
                }
            }
        }

    private fun createAuthHttpClient(): HttpClient {
        return HttpClient(httpClientEngine) {
            this.expectSuccess = true
            defaultRequest { url(buildConfig.baseUrl) }

            install(ContentNegotiation) { json(json) }
            install(ContentEncoding)
            if (enableNetworkLogs) {
                install(Logging) {
                    logger = Logger.SIMPLE
                    level = LogLevel.ALL
                }
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        if (tokenManager.getAccessToken().isNullOrEmpty()) {
                            return@loadTokens null
                        }
                        appLogger.breadcrumb("Auth", "Loading user tokens")
                        BearerTokens(
                            accessToken = tokenManager.getAccessToken().orEmpty(),
                            refreshToken = tokenManager.getRefreshToken().orEmpty(),
                        )
                    }

                    refreshTokens {
                        appLogger.breadcrumb("Auth", "Refreshing tokens...")
                        try {
                            if (tokenManager.getRefreshToken().isNullOrEmpty()) {
                                return@refreshTokens null
                            }

                            val renewTokens =
                                client
                                    .get("/v1/auth/refresh") { markAsRefreshTokenRequest() }
                                    .body<LoginResponse>()
                            if (
                                renewTokens.accessToken != null && renewTokens.refreshToken != null
                            ) {
                                tokenManager.setAuthTokens(
                                    renewTokens.accessToken,
                                    renewTokens.refreshToken,
                                )
                                appLogger.info("Auth", "Refreshed tokens successfully.")
                                BearerTokens(
                                    accessToken = tokenManager.getAccessToken()!!,
                                    refreshToken = tokenManager.getRefreshToken()!!,
                                )
                            } else {
                                appLogger.debug(
                                    "Auth",
                                    "Refresh tokens invalid response: $renewTokens",
                                )
                                sessionManager.logout()
                                null
                            }
                        } catch (e: Exception) {
                            sessionManager.logout()
                            appLogger.error("Auth", "refreshTokens: Exception: $e", e)
                            null
                        }
                    }
                }
            }
            install(refreshTokenPlugin)
        }
    }
}
