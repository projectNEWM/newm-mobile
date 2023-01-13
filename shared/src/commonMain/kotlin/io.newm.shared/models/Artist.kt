package io.newm.shared.models

data class Artist(
    val image: String,
    val name: String,
    val genre: String,//should this be an enum?
    val stars: Int,
    val id: String,
)