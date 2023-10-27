package io.newm.shared.repositories

import co.touchlab.kermit.Logger
import io.newm.shared.services.CardanoWalletAPI
import io.newm.shared.services.LedgerAssetMetadata
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal interface CardanoWalletRepository {
    suspend fun getWalletNFTs(xpub: String): List<NFTTrack>
}

internal class CardanoWalletRepositoryImpl : KoinComponent, CardanoWalletRepository {

    private val service: CardanoWalletAPI by inject()
    private val logger = Logger.withTag("NewmKMM-CardanoWalletRepository")

    override suspend fun getWalletNFTs(xpub: String): List<NFTTrack> {
        val walletNFTs = service.getWalletNFTs(xpub)
        val tracks = walletNFTs.mapNotNull {
            when (it.getMusicMetadataVersion()) {
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

data class NFTTrack(
    val name: String,
    val image: String,
    val imageMimeType: String,
    val files: List<File>,
    val musicMetadataVersion: Int,
    val releaseType: String?,
    val releaseTitle: String?,
    val distributor: String?,
    val publicationDate: String?
)

data class File(
    val name: String,
    val mediaType: String,
    val src: String
)

fun List<LedgerAssetMetadata>.getTrackFromMusicMetadataV2(): NFTTrack? {
    val name = this.find { it.key == "name" }?.value ?: return null
    val image = this.find { it.key == "image" }?.value ?: return null
    val imageMimeType = this.find { it.key == "mediaType" }?.value ?: return null
    val musicMetadataVersion =
        this.find { it.key == "music_metadata_version" }?.value?.toInt() ?: return null

    val fileList = this.find { it.key == "files" }?.children?.mapNotNull {
        val fileName =
            it.children.find { child -> child.key == "name" }?.value ?: return@mapNotNull null
        val fileMediaType =
            it.children.find { child -> child.key == "mediaType" }?.value ?: return@mapNotNull null
        val fileSrc =
            it.children.find { child -> child.key == "src" }?.value ?: return@mapNotNull null
        File(fileName, fileMediaType, fileSrc)
    }.orEmpty()

    val release = this.find { it.key == "release" }?.children
    val releaseType = release?.find { it.key == "release_type" }?.value
    val releaseTitle = release?.find { it.key == "release_title" }?.value
    val distributor = release?.find { it.key == "distributor" }?.value
    val publicationDate = release?.find { it.key == "publication_date" }?.value

    return NFTTrack(
        name,
        image,
        imageMimeType,
        fileList,
        musicMetadataVersion,
        releaseType,
        releaseTitle,
        distributor,
        publicationDate
    )
}
