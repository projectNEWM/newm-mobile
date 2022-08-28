package io.projectnewm.shared.models

public data class Song (
    val image: String,
    val title: String,
    val artist: Artist,
    val isNft: Boolean,
    val songId: String,
)