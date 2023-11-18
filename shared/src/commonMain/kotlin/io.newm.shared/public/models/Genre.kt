package io.newm.shared.public.models

import kotlinx.serialization.Serializable

/**
 * A data class representing a music genre.
 *
 * This class encapsulates information about a specific genre, including an optional identifier
 * and the genre's name. Both fields are nullable, allowing flexibility in cases where partial
 * or incomplete genre data might be encountered.
 *
 * @property genre_id The unique identifier of the genre, if available. It's nullable to accommodate
 *                    situations where the genre ID might not be known or applicable.
 * @property name The name of the genre. This field is nullable to account for cases where the genre
 *                name might not be provided or known.
 */
@Serializable
data class Genre(
    val genre_id: Int? = null,
    val name: String? = null,
)
