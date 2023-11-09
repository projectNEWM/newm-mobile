package io.newm.shared.public.models

import io.newm.shared.public.models.Genre
import kotlinx.serialization.Serializable

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
