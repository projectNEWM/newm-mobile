package com.projectnewm.example

import io.ktor.client.*
import io.ktor.client.request.*

interface ExampleService {
    suspend fun get(): List<ExampleDomainModel>
}

class ExampleServiceImpl(
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