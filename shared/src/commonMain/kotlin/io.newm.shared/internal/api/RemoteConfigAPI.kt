package io.newm.shared.internal.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.newm.shared.di.NetworkClientFactory
import io.newm.shared.internal.api.models.MobileConfig
import org.koin.core.component.KoinComponent

internal class RemoteConfigAPI(networkClient: NetworkClientFactory) : KoinComponent {

    private val httpClient: HttpClient = networkClient.httpClient()

    suspend fun getMobileConfig(): MobileConfig =
        httpClient.get("v1/mobile-config") {
            accept(ContentType.Application.Json)
        }.body()
}