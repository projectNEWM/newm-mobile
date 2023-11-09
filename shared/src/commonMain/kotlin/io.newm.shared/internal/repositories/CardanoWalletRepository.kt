package io.newm.shared.internal.repositories

import co.touchlab.kermit.Logger
import io.newm.shared.internal.services.CardanoWalletAPI
import io.newm.shared.internal.services.LedgerAssetMetadata
import io.newm.shared.public.models.NFTTrack
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


internal class CardanoWalletRepository : KoinComponent {

    private val service: CardanoWalletAPI by inject()
    private val logger = Logger.withTag("NewmKMM-CardanoWalletRepository")

    suspend fun getWalletNFTs(xpub: String): List<NFTTrack> {
        val walletNFTs = service.getWalletNFTs(xpub)
        val tracks = walletNFTs.mapNotNull {
            when (it.getMusicMetadataVersion()) {
                1 -> it.getTrackFromMusicMetadataV1()
                2 -> it.getTrackFromMusicMetadataV2()
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


data class File(
    val name: String,
    val mediaType: String,
    val src: String
)

fun List<LedgerAssetMetadata>.getTrackFromMusicMetadataV1(): NFTTrack? {
    val imageId = this.find { it.key == "image" }?.value ?: return null
    val artistNames = this.find { it.key == "artists" }
        ?.children
        ?.flatMap { artist ->
            artist.children.mapNotNull { detail ->
                if (detail.key == "name") detail.value else null
            }
        }.orEmpty()

    val songSrcId = this.find { it.key == "image" }?.value ?: return null

    return NFTTrack(
        name = find { it.key == "song_title" }?.value ?: return null,
        imageUrl = "https://arweave.net/${imageId.replace("ar://", "")}",
        songUrl =
        "https://arweave.net/${
            songSrcId.replace(
                "ar://",
                ""
            )
        }",
        artists = artistNames,
    )
}


fun List<LedgerAssetMetadata>.getTrackFromMusicMetadataV2(): NFTTrack? {
    val name = this.find { it.key == "name" }?.value ?: return null
    val image = this.find { it.key == "image" }?.value ?: return null

    var source = ""
    this.find { it.key == "files" }?.children?.mapNotNull {
        val fileName =
            it.children.find { child -> child.key == "name" }?.value ?: return@mapNotNull null
        val fileMediaType =
            it.children.find { child -> child.key == "mediaType" }?.value ?: return@mapNotNull null
        source =
            it.children.find { child -> child.key == "src" }?.value ?: return@mapNotNull null
        File(fileName, fileMediaType, source)
    }.orEmpty()

    return NFTTrack(
        name = name,
        imageUrl = "https://arweave.net/${image.replace("ar://", "")}",
        songUrl = "https://arweave.net/${source.replace("ar://", "")}",
        artists = listOf("{artistsNames}"),
    )
}
