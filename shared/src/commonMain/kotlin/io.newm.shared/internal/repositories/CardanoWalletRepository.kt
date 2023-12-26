package io.newm.shared.internal.repositories

import co.touchlab.kermit.Logger
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import io.newm.shared.internal.db.NewmDatabaseWrapper
import io.newm.shared.internal.services.CardanoWalletAPI
import io.newm.shared.internal.services.NewmPolicyIdsAPI
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.models.error.KMMException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

internal class CardanoWalletRepository(
    private val service: CardanoWalletAPI,
    private val scope: CoroutineScope,
    private val connectWalletManager: ConnectWalletManager,
    private val policyIdsRepository: NewmPolicyIdsRepository,
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


    fun getWalletCollectableTracks(): Flow<List<NFTTrack>> =
        combine(
            getWalletNFTs(),
            policyIdsRepository.getPolicyIds()
        ) { walletNFTs, policyIds ->
            walletNFTs.filter { track ->
                track.policyId !in policyIds
            }
        }

    fun getWalletStreamTokens(): Flow<List<NFTTrack>> = combine(
        getWalletNFTs(),
        policyIdsRepository.getPolicyIds()
    ) { walletNFTs, policyIds ->
        walletNFTs.filter { track ->
            track.policyId in policyIds
        }
    }

    fun deleteAllNFTs() {
        database.transaction {
            database.nFTTrackQueries.deleteAllTracks()
        }
    }

    private fun getWalletNFTs(): Flow<List<NFTTrack>> = database.nFTTrackQueries.selectAllTracks()
        .asFlow()
        .mapToList()
        .onStart {
            // Triggered when the Flow starts collecting
            if (database.nFTTrackQueries.selectAllTracks().executeAsList().isEmpty()) {
                logger.d { "No tracks found in DB, fetching from network" }
                scope.launch {
                    fetchNFTTracksFromNetwork()
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

    private suspend fun fetchNFTTracksFromNetwork(): List<NFTTrack> {
        try {
            val xpub = connectWalletManager.getXpub() ?: throw KMMException("No xpub found")
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
}
