package io.newm.shared.models

import io.newm.shared.models.User

data class Playlist (
    val image: String,
    val title: String,
    val creator: User,
    val songCount: Int,
    val playlistId: String,
    val genre: String,//TODO: should this be an enum?
    val starCount: Int,
    val playCount: Int,
)