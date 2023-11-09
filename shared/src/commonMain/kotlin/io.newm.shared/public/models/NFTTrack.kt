package io.newm.shared.public.models


data class NFTTrack(
    val name: String,
    val imageUrl: String,
    val songUrl: String,
    val artists: List<String> = emptyList(),
)