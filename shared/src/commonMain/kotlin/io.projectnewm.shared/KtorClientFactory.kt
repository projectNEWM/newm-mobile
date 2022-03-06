package io.projectnewm.shared

import io.ktor.client.*

expect class KtorClientFactory() {
    fun build(): HttpClient
}