package io.newm.shared.internal.repositories.parsers

import co.touchlab.kermit.Logger
import io.newm.shared.internal.services.LedgerAssetMetadata
import io.newm.shared.public.models.NFTTrack
import kotlin.time.Duration

fun List<LedgerAssetMetadata>.getMusicMetadataVersion(): Int {
    // Search for the 'music_metadata_version' key in the list
    val metadataVersion = this.find { it.key == "music_metadata_version" }?.value

    // Check if the key exists and what the version is
    return when (metadataVersion) {
        "1", "v1" -> 1 // Return 1 if the value is "1" or "v1"
        "2", "v2" -> 2 // Return 2 if the value is "2" or "v2"
        else -> 0 // Default to 0 if key not found or value is none of the above
    }
}

fun flattenLedgerMetadata(
    metadataList: List<LedgerAssetMetadata>,
    parentKey: String = ""
): Map<String, Any> {
    val resultMap = mutableMapOf<String, Any>()

    for (metadata in metadataList) {
        val key = if (parentKey.isNotEmpty()) "$parentKey.${metadata.key}" else metadata.key
        val valueType = metadata.valueType

        when {
            metadata.children.isEmpty() -> {
                if (valueType == "array") {
                    // Handle arrays by splitting elements and adding them to the map
                    val arrayElements = metadata.value.split(",").map { it.trim() }
                    for ((index, element) in arrayElements.withIndex()) {
                        resultMap["$key[$index]"] = element
                    }
                } else {
                    resultMap[key] = metadata.value
                }
            }

            else -> {
                val flattenedChildren = flattenLedgerMetadata(metadata.children, key)
                resultMap.putAll(flattenedChildren)
            }
        }
    }

    return resultMap
}

fun List<LedgerAssetMetadata>.getTrackFromMusicMetadataV1(logger: Logger): NFTTrack? {
    val flattenedMetadata = flattenLedgerMetadata(this)

    val imageId = flattenedMetadata["image"] as? String
    val name = flattenedMetadata.getTrackName()
    val src = flattenedMetadata["files.files.src"] as? String

    val duration = flattenedMetadata.getDuration()

    val artists = mutableListOf<String>()
    for (key in flattenedMetadata.keys) {
        if (key.startsWith("artists.artists")
            || key.startsWith("artists.artists.name")
            || key.startsWith("artists")
            || key.startsWith("featured_artists")
        ) {
            val artistName = flattenedMetadata[key] as? String
            if (artistName != null) {
                artists.add(artistName)
            }
        }
    }

    if (src == null || name == null || imageId == null || duration == null) {
        logger.d { "SKIPPED SONG with" }
        if (src == null) logger.d { "src is null" }
        if (name == null) logger.d { "name is null" }
        if (imageId == null) logger.d { "imageId is null" }
        if (duration == null) logger.d { "duration is null" }
        return null
    }

    return NFTTrack(
        id = src.toId(logger),
        name = name,
        imageUrl = imageId.toResourceUri(logger),
        songUrl = src.toResourceUri(logger),
        duration = duration,
        artists = artists
    )
}

private fun String.toResourceUri(logger: Logger): String {
    return when {
        this.startsWith("ipfs://") -> {
            val ipfs = "https://bcsh.mypinata.cloud/ipfs/"
            "$ipfs${this.replace("ipfs://", "")}"
        }

        this.startsWith("ar://") -> {
            val arweave = "https://arweave.net/"
            "$arweave${this.replace("ar://", "")}"
        }

        else -> {
            logger.e { "Unsupported resource type: $this" }
            this
        }
    }
}

private fun String.toId(logger: Logger): String {
    return when {
        this.contains("://") -> {
            "trackId:" + this.replace("://", "")
        }

        else -> {
            logger.e { "Unsupported resource type: $this" }
            this
        }
    }
}

fun List<LedgerAssetMetadata>.getTrackFromMusicMetadataV2(logger: Logger): NFTTrack? {
    val flattenedMetadata = flattenLedgerMetadata(this)

    val image = flattenedMetadata["image"] as? String
    val name = flattenedMetadata.getTrackName()
    val source = flattenedMetadata["files.files.src"] as? String
    val duration = flattenedMetadata.getDuration()
    val artists = flattenedMetadata.getArtistNames()

    if (source == null || name == null || image == null || duration == null) {
        logger.d { "SKIPPED SONG with" }
        if (source == null) logger.d { "src is null" }
        if (name == null) logger.d { "name is null" }
        if (image == null) logger.d { "imageId is null" }
        if (duration == null) logger.d { "duration is null" }
        return null
    }

    return NFTTrack(
        id = source.toId(logger),
        name = name,
        imageUrl = image.toResourceUri(logger),
        songUrl = source.toResourceUri(logger),
        artists = artists,
        duration = duration
    )
}

private fun Map<String, Any>.getArtistNames(): List<String> {
    val artists = mutableListOf<String>()
    for (key in keys) {
        if (key.startsWith("artists.artists")
            || key.startsWith("artists")
            || key.startsWith("artists.artists.name")
            || key.startsWith("featured_artists")
            || key.startsWith("files.files.song.artists.artists.name")
            || key.startsWith("release.artists.artists")
            || key.startsWith("release.artists")
        ) {
            val artistName = this[key] as? String
            if (artistName != null) {
                artists.add(artistName)
            }
        }
    }
    return artists
}

private fun Map<String, Any>.getTrackName(): String? {
    for (key in keys) {
        if (key.startsWith("files.files.song.song_title")
            || key.startsWith("song_title")
            || key.startsWith("release.song_title")
            || key.startsWith("files.files.song_title")
        ) {
            val trackName = this[key] as? String
            if (trackName != null) {
                return trackName
            }
        }
    }
    return null
}

private fun Map<String, Any>.getDuration(): Long? {
    for (key in keys) {
        if (key.startsWith("release.song_duration")
            || key.startsWith("files.files.song.song_duration")
            || key.startsWith("files.files.song_duration")
            || key.startsWith("song_duration")
        ) {
            val duration = this[key] as? String
            if (duration != null) {
                return Duration.parse(duration).inWholeSeconds
            }
        }
    }
    return null
}