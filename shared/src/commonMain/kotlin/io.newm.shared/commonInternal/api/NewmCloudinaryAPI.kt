package io.newm.shared.commonInternal.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.newm.shared.commonInternal.api.models.CloudinarySignResponse
import org.koin.core.component.KoinComponent

class NewmCloudinaryAPI(
    private val authClient: HttpClient,
) : KoinComponent {
    suspend fun sign(options: Map<String, Any>): CloudinarySignResponse =
        authClient
            .post("/v1/cloudinary/sign") {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                setBody(HashMap(options))
            }.body()
}
