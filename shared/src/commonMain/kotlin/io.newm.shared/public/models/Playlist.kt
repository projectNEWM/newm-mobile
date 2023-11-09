package io.newm.shared.public.models

import kotlinx.serialization.Serializable

@Serializable
data class Playlist(
    val id: String,
    val ownerId: String,
    val createdAt: String,
    val name: String
)