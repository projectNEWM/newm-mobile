package io.newm.shared.internal.repositories

import co.touchlab.kermit.Logger
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import io.newm.shared.internal.db.NewmDatabaseWrapper
import io.newm.shared.internal.services.CardanoWalletAPI
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.models.error.KMMException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

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
                policyId = track.policyId,
                title = track.title,
                assetName = track.assetName,
                amount = track.amount,
                imageUrl = track.imageUrl,
                audioUrl = track.audioUrl,
                duration = track.duration,
                artists = track.artists.split(","),
                genres = track.genres.split(","),
                moods = track.moods.split(","),
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
                    policyId = track.policyId,
                    title = track.title,
                    assetName = track.assetName,
                    amount = track.amount,
                    imageUrl = track.imageUrl,
                    audioUrl = track.audioUrl,
                    duration = track.duration,
                    artists = track.artists.split(","),
                    genres = track.genres.split(","),
                    moods = track.genres.split(",")
                )
            }
        }


    suspend fun getWalletNFTs(): List<NFTTrack> {
        val xpub = connectWalletManager.getXpub() ?: throw KMMException("No xpub found")
        val tracks = fetchNFTTracksFromNetwork(xpub)
//        cacheNFTTracks(tracks)
        logger.d { "Result Size: ${tracks.size}" }
        return tracks
    }

    private suspend fun fetchNFTTracksFromNetwork(xpub: String): List<NFTTrack> {
        try {
            val walletNFTs = service.getWalletNFTs(xpub)
            cacheNFTTracks(walletNFTs)
            return walletNFTs
        } catch (e: Exception) {
            logger.e(e) { "Error fetching NFTs from network ${e.cause}" }
            throw e
        }
    }

    private fun cacheNFTTracks(nftTracks: List<NFTTrack>) {
        database.transaction {
            nftTracks.forEach { track ->
                database.nFTTrackQueries.insertOrReplaceTrack(
                    id = track.id,
                    policyId = track.policyId,
                    title = track.title,
                    assetName = track.assetName,
                    amount = track.amount,
                    imageUrl = track.imageUrl,
                    audioUrl = track.audioUrl,
                    duration = track.duration,
                    artists = track.artists.joinToString(","),
                    genres = track.genres.joinToString(","),
                    moods = track.moods.joinToString(",")
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
