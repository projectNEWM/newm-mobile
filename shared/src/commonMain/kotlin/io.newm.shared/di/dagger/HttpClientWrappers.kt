package io.newm.shared.di.dagger

import io.ktor.client.HttpClient

class AuthHttpClient(
    val client: HttpClient,
)

class BaseHttpClient(
    val client: HttpClient,
)
