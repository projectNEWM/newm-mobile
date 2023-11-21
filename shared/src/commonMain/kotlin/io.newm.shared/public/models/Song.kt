package io.newm.shared.public.models

import kotlinx.serialization.Serializable

/**
 * A data class representing a NEWM minted song.
 *
 * This class encapsulates the properties of a song, including its metadata, status in minting and marketplace,
 * and optional fields related to its digital and commercial attributes.
 *
 * @property id Unique identifier of the song.
 * @property ownerId Identifier of the owner of the song.
 * @property createdAt Timestamp of when the song was created.
 * @property title Title of the song.
 * @property genres List of genres associated with the song.
 * @property mintingStatus Status of the song in the context of NFT minting.
 * @property marketplaceStatus Status of the song in the context of marketplace availability.
 * @property moods List of moods associated with the song, optional.
 * @property coverArtUrl URL of the song's cover art, optional.
 * @property lyricsUrl URL where the lyrics of the song can be found, optional.
 * @property description Description or additional information about the song, optional.
 * @property album Album to which the song belongs, optional.
 * @property track Track number or identifier within the album, optional.
 * @property language Language of the song lyrics, optional.
 * @property copyrights Information about copyright claims or status, optional.
 * @property parentalAdvisory Information about parental advisory status, optional.
 * @property barcodeType Type of barcode used for the song, optional.
 * @property barcodeNumber Number associated with the song's barcode, optional.
 * @property isrc International Standard Recording Code of the song, optional.
 * @property iswc International Standard Musical Work Code of the song, optional.
 * @property ipis Interested Parties Information System code related to the song, optional.
 * @property releaseDate Release date of the song, optional.
 * @property duration Duration of the song in seconds, optional.
 * @property streamUrl URL for streaming the song, optional.
 * @property nftPolicyId Policy ID if the song is registered as an NFT, optional.
 * @property nftName Name of the NFT if the song is registered as an NFT, optional.
 * @property tempSourceId Temporary source identifier for internal usage, defaults to 0.
 */
@Serializable
data class Song(
    val id: String,
    val ownerId: String,
    val createdAt: String,
    val title: String,
    val genres: List<Genre>,
    val mintingStatus: String,
    val marketplaceStatus: String,
    val moods: List<String>? = null,
    val coverArtUrl: String? = null,
    val lyricsUrl: String? = null,
    val description: String? = null,
    val album: String? = null,
    val track: String? = null,
    val language: String? = null,
    val copyrights: String? = null,
    val parentalAdvisory: String? = null,
    val barcodeType: String? = null,
    val barcodeNumber: String? = null,
    val isrc: String? = null,
    val iswc: String? = null,
    val ipis: String? = null,
    val releaseDate: String? = null,
    val duration: Int? = null,
    val streamUrl: String? = null,
    val nftPolicyId: String? = null,
    val nftName: String? = null,
    val tempSourceId: Int = 0
)
