package io.newm.shared.internal.entity

import kotlinx.serialization.Serializable

@Serializable
data class AuthData(
    val accessToken: String,
    val refreshToken: String
)