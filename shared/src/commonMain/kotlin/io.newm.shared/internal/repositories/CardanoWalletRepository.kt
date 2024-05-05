package io.newm.shared.internal.repositories

import co.touchlab.kermit.Logger
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import io.newm.shared.internal.db.NewmDatabaseWrapper
import io.newm.shared.internal.services.CardanoWalletAPI
import io.newm.shared.internal.services.NEWMWalletConnectionAPI
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.models.WalletConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

internal class CardanoWalletRepository(
    private val service: CardanoWalletAPI,
    private val walletConnectionAPI: NEWMWalletConnectionAPI,
    private val scope: CoroutineScope,
    private val policyIdsRepository: NewmPolicyIdsRepository,
    private val db: NewmDatabaseWrapper,
) : KoinComponent {

    private val logger = Logger.withTag("NewmKMM-CardanoWalletRepository")

    fun getTrack(id: String): NFTTrack? =
        db().nFTTrackQueries.selectTrackById(id).executeAsOneOrNull()?.let { track ->
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
        db().transaction {
            db().nFTTrackQueries.deleteAll()
        }
    }

    fun getWalletConnections(): Flow<List<WalletConnection>> =
        db().walletConnectionQueries.getAll()
            .asFlow()
            .mapToList()
            .onStart {
                scope.launch {
                    fetchWalletConnections()
                }
            }
            .map { dbWalletConnections ->
                dbWalletConnections.map { wallet ->
                    WalletConnection(
                        id = wallet.id,
                        createdAt = wallet.createdAt,
                        stakeAddress = wallet.stakeAddress
                    )
                }
            }

    private fun getWalletNFTs(): Flow<List<NFTTrack>> = db().nFTTrackQueries.selectAllTracks()
        .asFlow()
        .mapToList()
        .onStart {
            // Triggered when the Flow starts collecting
            scope.launch {
                fetchNFTTracksFromNetwork()
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

    internal suspend fun fetchNFTTracksFromNetwork() {
        try {
            val walletNFTs = service.getWalletNFTs()
            cacheNFTTracks(walletNFTs)
        } catch (e: Exception) {
            logger.e(e) { "Error fetching NFTs from network ${e.cause}" }
            throw e
        }
    }

    private suspend fun fetchWalletConnections() {
        try {
            val walletConnections = walletConnectionAPI.getWalletConnections()
            logger.d { "cje466 Fetched wallet connections from network: $walletConnections" }
            cacheWalletConnections(walletConnections)
        } catch (e: Exception) {
            logger.e(e) { " cje466 Error fetching wallet connections from network ${e.cause}" }
            throw e
        }
    }

    private fun cacheNFTTracks(nftTracks: List<NFTTrack>) {
        db().transaction {
            nftTracks.forEach { track ->
                db().nFTTrackQueries.insertOrReplaceTrack(
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

    private fun cacheWalletConnections(walletConnections: List<WalletConnection>) {
        db().transaction {
            walletConnections.forEach { connection ->
                db().walletConnectionQueries.insert(
                    id = connection.id,
                    createdAt = connection.createdAt,
                    stakeAddress = connection.stakeAddress
                )
            }
        }
    }

    fun connectWallet(newmCode: String) {
        scope.launch {
            walletConnectionAPI.connectWallet(newmCode.removePrefix("newm-"))
            fetchWalletConnections()
        }
    }

    fun disconnectWallet(walletConnectionId: String? = null) {
        scope.launch {
            walletConnectionId?.let {
                walletConnectionAPI.disconnectWallet(it)
            } ?: run {
                getWalletConnections().map { connections ->
                    connections.forEach { connection ->
                        walletConnectionAPI.disconnectWallet(connection.id)
                    }
                }
            }

        }
    }
}
