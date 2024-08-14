package io.newm.shared.internal.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.newm.shared.di.NetworkClientFactory
import io.newm.shared.internal.api.models.MobileConfig
import io.newm.shared.internal.api.utils.addHumanVerificationCodeToHeader
import org.koin.core.component.KoinComponent

internal class RemoteConfigAPI(networkClient: NetworkClientFactory) {

    private val httpClient: HttpClient = networkClient.httpClient()

    suspend fun getMobileConfig(humanVerificationCode: String): MobileConfig =
        httpClient.get("/v1/client-config/mobile") {
            accept(ContentType.Application.Json)
            addHumanVerificationCodeToHeader(humanVerificationCode)
        }.body()
}