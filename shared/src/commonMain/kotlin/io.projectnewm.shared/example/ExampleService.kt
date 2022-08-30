package io.projectnewm.shared.example

import io.ktor.client.*
import io.ktor.client.request.*

internal interface ExampleService {
    suspend fun get(): List<ExampleDomainModel>
}

internal class ExampleServiceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String = BASE_URL,
) : ExampleService {

    override suspend fun get(): List<ExampleDomainModel> {
        return httpClient.get<List<ExampleResponse>> {
            url("$baseUrl/posts")
        }.toExampleDomainModelList()
    }

    companion object {
        const val BASE_URL = "https://my-json-server.typicode.com/typicode/demo/"
    }
}