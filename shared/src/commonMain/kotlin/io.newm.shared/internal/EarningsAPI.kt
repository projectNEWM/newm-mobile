package io.newm.shared.internal

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.newm.shared.NewmAppLogger
import io.newm.shared.di.NetworkClientFactory
import io.newm.shared.internal.api.models.EarningsResponse
import io.newm.shared.internal.api.utils.addHumanVerificationCodeToHeader

internal class EarningsAPI(networkClient: NetworkClientFactory, val logger: NewmAppLogger) {
    private val authHttpClient: HttpClient = networkClient.authHttpClient()

    suspend fun getEarningsForWalletId(walletAddress: String, humanVerificationCode: String): EarningsResponse =
        authHttpClient.get("/v1/earnings/$walletAddress") {
            contentType(ContentType.Application.Json)
            addHumanVerificationCodeToHeader(humanVerificationCode)
        }.body()
}