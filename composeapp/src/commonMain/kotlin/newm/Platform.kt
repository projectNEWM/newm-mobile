package io.newm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform