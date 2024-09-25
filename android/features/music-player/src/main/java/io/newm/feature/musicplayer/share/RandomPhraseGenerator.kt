package io.newm.feature.musicplayer.share

import android.content.Context
import io.newm.core.resources.R

fun Context.getRandomSharePhrase(songTitle: String, songArtist: String): String {
    val phraseIds = listOf(
        R.string.share_phrase_1,
        R.string.share_phrase_2,
        R.string.share_phrase_3,
        R.string.share_phrase_4,
        R.string.share_phrase_5,
        R.string.share_phrase_6,
        R.string.share_phrase_7,
        R.string.share_phrase_8,
        R.string.share_phrase_9,
        R.string.share_phrase_10,
        R.string.share_phrase_11,
        R.string.share_phrase_12,
        R.string.share_phrase_13,
        R.string.share_phrase_14,
        R.string.share_phrase_15
    )
    return this.getString( phraseIds.random(), songTitle, songArtist)
}