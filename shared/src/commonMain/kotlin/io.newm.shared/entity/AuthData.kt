package io.newm.shared.entity

import kotlinx.serialization.Serializable

@Serializable
data class AuthData(
    val accessToken: String,
    val refreshToken: String
)