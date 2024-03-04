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

@Serializable
enum class SortOption(val description: String) {
    TitleAscending("Title Ascending"),
    TitleDescending("Title Descending"),
    ArtistAscending("Artist Ascending"),
    ArtistDescending("Artist Descending"),
    LengthAscending("Length Ascending"),
    LengthDescending("Length Descending");

    fun compare(s1: NFTTrack, s2: NFTTrack): Int {
        return when (this) {
            TitleAscending -> s1.title.compareTo(s2.title)
            TitleDescending -> s2.title.compareTo(s1.title)
            ArtistAscending -> s1.artists.firstOrNull()?.compareTo(s2.artists.firstOrNull() ?: "") ?: -1
            ArtistDescending -> s2.artists.firstOrNull()?.compareTo(s1.artists.firstOrNull() ?: "") ?: -1
            LengthAscending -> s1.duration.compareTo(s2.duration)
            LengthDescending -> s2.duration.compareTo(s1.duration)
        }
    }
}

@Serializable
data class FilterOptions(var searchText: String = "", var maxLength: Int? = null) {
    fun filter(track: NFTTrack): Boolean {
        val searchCriteriaMet = if (searchText.isEmpty()) true else {
            track.title.lowercase().contains(searchText.lowercase()) ||
                    track.artists.any { artist -> artist.lowercase().contains(searchText.lowercase()) }
        }
        val lengthCriteriaMet = maxLength?.let { track.duration <= it } ?: true

        return searchCriteriaMet && lengthCriteriaMet
    }
}
