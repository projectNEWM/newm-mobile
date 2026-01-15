package io.newm.shared.commonInternal.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.newm.shared.di.NetworkClientFactory
import io.newm.shared.commonInternal.api.models.MobileConfig
import io.newm.shared.commonInternal.api.utils.addHumanVerificationCodeToHeader
import org.koin.core.component.KoinComponent

class RemoteConfigAPI(private val httpClient: HttpClient) {

    suspend fun getMobileConfig(humanVerificationCode: String): MobileConfig =
        httpClient.get("/v1/client-config/mobile") {
            accept(ContentType.Application.Json)
            addHumanVerificationCodeToHeader(humanVerificationCode)
        }.body()
}