package io.newm.shared.public.models


/**
 * Data class representing a Non-Fungible Token (NFT) track.
 *
 * This class encapsulates the properties of an NFT track, including its unique identifier,
 * name, associated image and song URLs, duration, and a list of artists involved.
 *
 * @property id Unique identifier of the NFT track.
 * @property name Name of the NFT track.
 * @property imageUrl URL of the image associated with the NFT track.
 * @property songUrl URL of the song file associated with the NFT track.
 * @property duration Duration of the song in milliseconds.
 * @property artists A list of artist names associated with the NFT track. Defaults to an empty list if not provided.
 */
data class NFTTrack(
    val id: String,
    val name: String,
    val imageUrl: String,
    val songUrl: String,
    val duration: Long,
    val artists: List<String> = emptyList(),
)