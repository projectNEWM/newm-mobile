package io.newm.shared.commonInternal

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonInternal.api.models.EarningsResponse
import io.newm.shared.commonInternal.api.utils.addHumanVerificationCodeToHeader

class EarningsAPI(
    private val authHttpClient: HttpClient,
    val logger: NewmAppLogger,
) {
    suspend fun getEarningsForWalletId(
        walletAddress: String,
        humanVerificationCode: String,
    ): EarningsResponse =
        authHttpClient
            .get("/v1/earnings/$walletAddress") {
                contentType(ContentType.Application.Json)
                addHumanVerificationCodeToHeader(humanVerificationCode)
            }.body()
}
