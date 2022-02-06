package io.projectnewm

import io.ktor.client.*

expect class KtorClientFactory() {
    fun build(): HttpClient
}