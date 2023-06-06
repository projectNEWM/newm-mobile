package io.newm.shared.models

import kotlinx.serialization.Serializable


@Serializable
data class Genre(
    val genre_id: Int? = null,
    val name: String? = null,
)
