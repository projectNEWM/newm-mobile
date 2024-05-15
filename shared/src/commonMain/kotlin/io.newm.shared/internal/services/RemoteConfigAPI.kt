package io.newm.shared.internal.services

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.newm.shared.di.NetworkClientFactory
import io.newm.shared.internal.services.models.MobileConfig
import org.koin.core.component.KoinComponent

internal class RemoteConfigAPI(networkClient: NetworkClientFactory) : KoinComponent {

    private val httpClient: HttpClient = networkClient.httpClient()

    suspend fun getMobileConfig(): MobileConfig =
        httpClient.get("v1/mobile-config") {
            contentType(ContentType.Application.Json)
        }.body()
}