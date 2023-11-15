package io.newm.shared.public.models


data class NFTTrack(
    val id: String,
    val name: String,
    val imageUrl: String,
    val songUrl: String,
    val duration: String,
    val artists: List<String> = emptyList(),
)