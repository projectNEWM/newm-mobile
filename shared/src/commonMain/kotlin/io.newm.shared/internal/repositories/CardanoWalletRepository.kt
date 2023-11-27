package io.newm.shared.internal.repositories

import co.touchlab.kermit.Logger
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import io.newm.shared.db.cache.NewmDatabase
import io.newm.shared.internal.db.NewmDatabaseWrapper
import io.newm.shared.internal.repositories.parsers.getMusicMetadataVersion
import io.newm.shared.internal.repositories.parsers.getTrackFromMusicMetadataV1
import io.newm.shared.internal.repositories.parsers.getTrackFromMusicMetadataV2
import io.newm.shared.internal.services.CardanoWalletAPI
import io.newm.shared.internal.services.LedgerAssetMetadata
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.models.error.KMMException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import kotlin.time.Duration


internal class CardanoWalletRepository(
    private val service: CardanoWalletAPI,
    private val scope: CoroutineScope,
    private val connectWalletManager: ConnectWalletManager,
    dbWrapper: NewmDatabaseWrapper,
) : KoinComponent {

    private val logger = Logger.withTag("NewmKMM-CardanoWalletRepository")
    private val database = dbWrapper.instance ?: throw KMMException("Database not initialized")

    fun getTrack(id: String): NFTTrack? =
        database.nFTTrackQueries.selectTrackById(id).executeAsOneOrNull()?.let { track ->
            NFTTrack(
                id = track.id,
                name = track.name,
                imageUrl = track.imageUrl,
                songUrl = track.songUrl,
                duration = track.duration,
                artists = track.artists.split(",")
            )
        }

    fun getWalletNFTsFlow(): Flow<List<NFTTrack>> = database.nFTTrackQueries.selectAllTracks()
        .asFlow()
        .mapToList()
        .onStart {
            // Triggered when the Flow starts collecting
            if (database.nFTTrackQueries.selectAllTracks().executeAsList().isEmpty()) {
                logger.d { "No tracks found in DB, fetching from network" }
                scope.launch {
                    getWalletNFTs()
                }
            }

        }
        .map { tracksFromDb ->
            tracksFromDb.map { track ->
                NFTTrack(
                    id = track.id,
                    name = track.name,
                    imageUrl = track.imageUrl,
                    songUrl = track.songUrl,
                    duration = track.duration,
                    artists = track.artists.split(",").filterNot { it.isBlank() }
                )
            }
        }


    suspend fun getWalletNFTs(): List<NFTTrack> {
        val xpub = connectWalletManager.getXpub() ?: throw KMMException("No xpub found")
        val tracks = fetchNFTTracksFromNetwork(xpub)
        cacheNFTTracks(tracks)
        logger.d { "Result Size: ${tracks.size}" }
        return tracks
    }

    private suspend fun fetchNFTTracksFromNetwork(xpub: String): List<NFTTrack> {
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
        return tracks
    }

    private fun cacheNFTTracks(nftTracks: List<NFTTrack>) {
        database.transaction {
            nftTracks.forEach { track ->
                database.nFTTrackQueries.insertOrReplaceTrack(
                    id = track.id,
                    name = track.name,
                    imageUrl = track.imageUrl,
                    songUrl = track.songUrl,
                    duration = track.duration,
                    artists = track.artists.joinToString(separator = ","),
                )
            }
        }
    }

    fun deleteAllNFTs() {
        database.transaction {
            database.nFTTrackQueries.deleteAllTracks()
        }
    }
}
