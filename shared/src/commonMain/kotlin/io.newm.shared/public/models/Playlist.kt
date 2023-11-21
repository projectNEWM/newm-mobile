package io.newm.shared.public.models

import kotlinx.serialization.Serializable

/**
 * A data class representing a playlist.
 *
 * This class encapsulates the core attributes of a playlist, including its unique identifier,
 * the identifier of its owner, the timestamp when it was created, and its name.
 *
 * @property id Unique identifier of the playlist.
 * @property ownerId Identifier of the user who owns or created the playlist.
 * @property createdAt Timestamp representing when the playlist was created. Typically in a standardized
 *                     format such as ISO 8601.
 * @property name The name of the playlist, as given by the owner or creator.
 */
@Serializable
data class Playlist(
    val id: String,
    val ownerId: String,
    val createdAt: String,
    val name: String
)