package io.newm.shared.database.entries

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.newm.shared.database.sqlite.DataConverters
import io.newm.shared.public.models.NFTTrack
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
@Entity
@Serializable
data class DBNFTTrack(
    @PrimaryKey(autoGenerate = false)
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
    val artists: List<String>,
    @SerialName("genres")
    val genres: List<String>,
    @SerialName("moods")
    val moods: List<String>,
    val isDownloaded: Boolean = false
)

fun DBNFTTrack.toNFTTrack(): NFTTrack {
    return NFTTrack(
        id = id,
        policyId = policyId,
        title = title,
        assetName = assetName,
        amount = amount,
        imageUrl = imageUrl,
        audioUrl = audioUrl,
        duration = duration,
        artists = artists,
        genres = genres,
        moods = moods,
        isDownloaded = isDownloaded
    )
}

fun NFTTrack.toDBNFTTrack(): DBNFTTrack {
    return DBNFTTrack(
        id = id,
        policyId = policyId,
        title = title,
        assetName = assetName,
        amount = amount,
        imageUrl = imageUrl,
        audioUrl = audioUrl,
        duration = duration,
        artists = artists,
        genres = genres,
        moods = moods,
        isDownloaded = isDownloaded
    )
}