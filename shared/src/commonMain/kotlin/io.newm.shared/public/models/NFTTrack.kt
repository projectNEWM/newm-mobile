package io.newm.shared.public.models


data class NFTTrack(
    val id: String,
    val name: String,
    val imageUrl: String,
    val songUrl: String,
    val duration: Long,
    val artists: List<String> = emptyList(),
)