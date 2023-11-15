package io.newm.shared.internal.repositories

import co.touchlab.kermit.Logger
import io.newm.shared.internal.services.CardanoWalletAPI
import io.newm.shared.internal.services.LedgerAssetMetadata
import io.newm.shared.public.models.NFTTrack
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration


internal class CardanoWalletRepository : KoinComponent {

    private val service: CardanoWalletAPI by inject()
    private val logger = Logger.withTag("NewmKMM-CardanoWalletRepository")

    suspend fun getWalletNFTs(xpub: String): List<NFTTrack> {
        val walletNFTs = service.getWalletNFTs(xpub)
        val tracks = walletNFTs.mapNotNull {
            when (it.getMusicMetadataVersion()) {
                1 -> it.getTrackFromMusicMetadataV1(logger)
                2 -> it.getTrackFromMusicMetadataV2(logger)
                else -> {
                    logger.d { "Unsupported music metadata version: ${it.getMusicMetadataVersion()}" }
                    null
                }
            }
        }
        logger.d { "Result Size: ${tracks.size}" }
        return tracks
    }
}

fun List<LedgerAssetMetadata>.getMusicMetadataVersion(): Int? {
    return this.find { it.key == "music_metadata_version" }?.value?.toInt() ?: return null
}

fun List<LedgerAssetMetadata>.getTrackFromMusicMetadataV1(logger: Logger): NFTTrack? {
    var imageId: String? = null
    var name: String? = null
    var src: String? = null
    var duration: Long? = null
    var artists: List<String> = mutableListOf()

    this.forEach { metadata ->
        when (metadata.key) {
            "image" -> {
                imageId = metadata.value
            }

            "song_title" -> {
                name = metadata.value
            }

            "files" -> {
                metadata.children.forEach { files ->
                    val isThereAudioFiles = files.children.find { file ->
                        file.key == "mediaType" && file.value.contains("audio")
                    } != null

                    if (isThereAudioFiles) {
                        files.children.first { file ->
                            file.key == "src"
                        }.let { file ->
                            src = file.value
                        }
                    }
                }
            }

            "artists" -> {
                metadata.children.forEach { artist ->
                    artist.children.first { detail ->
                        detail.key == "name"
                    }.let { detail ->
                        artists = artists.plus(detail.value)
                    }
                }
            }

            "song_duration" -> {
                duration = Duration.parse(metadata.value).inWholeSeconds
            }
        }
    }
    if (src == null || name == null || imageId == null || duration == null) {
        logger.d { "SKIPPED SONG with" }
        when {
            src == null -> logger.d { "src is null" }
            name == null -> logger.d { "name is null" }
            imageId == null -> logger.d { "imageId is null" }
            duration == null -> logger.d { "duration is null" }
        }
        return null
    }
    return NFTTrack(
        id = src!!.toId(logger),
        name = name!!,
        imageUrl = imageId!!.toResourceUri(logger),
        songUrl = src!!.toResourceUri(logger),
        duration = duration!!,
        artists = artists,
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
        this.startsWith("ipfs://") -> {
            this.replace("ipfs://", "")
        }

        this.startsWith("ar://") -> {
            this.replace("ar://", "")
        }

        else -> {
            logger.e { "Unsupported resource type: $this" }
            this
        }
    }
}


fun List<LedgerAssetMetadata>.getTrackFromMusicMetadataV2(logger: Logger): NFTTrack? {
    var image: String? = null
    var name: String? = null
    var source: String? = null
    var duration: Long? = null
    val artistSet = mutableSetOf<String>()

    this.forEach { metadata ->
        when (metadata.key) {
            "image" -> {
                image = metadata.value
            }

            "files" -> {
                metadata.children.forEach { files ->
                    val isThereAudioFiles = files.children.find { file ->
                        file.key == "mediaType" && file.value.contains("audio")
                    } != null

                    if (isThereAudioFiles) {
                        files.children.forEach { file ->
                            when (file.key) {
                                "src" -> {
                                    source = file.value
                                }

                                "song" -> {
                                    file.children.forEach { song ->
                                        when (song.key) {
                                            "song_title" -> {
                                                name = song.value
                                            }

                                            "song_duration" -> {
                                                duration =
                                                    Duration.parseIsoString(song.value).inWholeSeconds
                                            }

                                            "artists" -> {
                                                song.children.forEach { listArtist ->
                                                    when (listArtist.key) {
                                                        "artists" -> {
                                                            listArtist.children.forEach { artist ->
                                                                when (artist.key) {
                                                                    "name" -> {
                                                                        artistSet.add(artist.value)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }

                }
            }
        }

    }

    if (source == null || name == null || image == null || duration == null) {
        logger.d { "SKIPPED SONG with" }
        when {
            source == null -> logger.d { "src is null" }
            name == null -> logger.d { "name is null" }
            image == null -> logger.d { "imageId is null" }
            duration == null -> logger.d { "duration is null" }
        }
        return null
    }

    return NFTTrack(
        id = source!!.toId(logger),
        name = name!!,
        imageUrl = image!!.toResourceUri(logger),
        songUrl = source!!.toResourceUri(logger),
        artists = artistSet.toList(),
        duration = duration!!,
    )
}
