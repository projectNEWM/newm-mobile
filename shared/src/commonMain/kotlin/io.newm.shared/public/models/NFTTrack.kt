package io.newm.shared.public.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Data class representing a Non-Fungible Token (NFT) track.
 *
 * This class encapsulates the properties of an NFT track, including its unique identifier,
 * name, associated image and song URLs, duration, and a list of artists involved.
 *
 * @property id Unique identifier of the NFT track.
 * @property policyId Unique identifier of the policy associated with the NFT track.
 * @property title Name of the NFT track.
 * @property assetName Name of the NFT track asset.
 * @property imageUrl URL of the image associated with the NFT track.
 * @property audioUrl URL of the song file associated with the NFT track.
 * @property duration Duration of the song in seconds.
 * @property artists A list of artist names associated with the NFT track. Defaults to an empty list if not provided.
 * @property genres A list of genres associated with the NFT track. Defaults to an empty list if not provided.
 * @property moods A list of moods associated with the NFT track. Defaults to an empty list if not provided.
 */
@Serializable
data class NFTTrack(
    @SerialName("id")
    val id: String,
    @SerialName("policyId")
    val policyId: String,
    @SerialName("title")
    val title: String,
    @SerialName("assetName")
    val assetName: String,
    @SerialName("amount")
    val amount: Long,
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("audioUrl")
    val audioUrl: String,
    @SerialName("duration")
    val duration: Long,
    @SerialName("artists")
    val artists: List<String> = emptyList(),
    @SerialName("genres")
    val genres: List<String>,
    @SerialName("moods")
    val moods: List<String> = emptyList(),
    val isDownloaded: Boolean = false,
)
