package io.newm.shared.internal.repositories

import co.touchlab.kermit.Logger
import io.newm.shared.internal.db.NewmDatabaseWrapper
import io.newm.shared.internal.db.cacheNFTTracks
import io.newm.shared.internal.db.cacheWalletConnections
import io.newm.shared.internal.db.deleteAllNFTs
import io.newm.shared.internal.db.deleteAllWalletConnections
import io.newm.shared.internal.db.getAllTracks
import io.newm.shared.internal.db.getTrack
import io.newm.shared.internal.db.getWalletConnections
import io.newm.shared.internal.services.CardanoWalletAPI
import io.newm.shared.internal.services.NEWMWalletConnectionAPI
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.models.WalletConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
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

    fun getTrackCache(id: String): NFTTrack? = db.getTrack(id)

    fun getWalletCollectableTracks(): Flow<List<NFTTrack>> =
        combine(
            getWalletNFTsCache(),
            policyIdsRepository.getPolicyIds()
        ) { walletNFTs, policyIds ->
            walletNFTs.filter { track ->
                track.policyId !in policyIds
            }
        }.onStart {
            scope.launch {
                try {
                    val nfts = getWalletNFTsNetwork()
                    db.cacheNFTTracks(nfts)
                } catch (e: Exception) {
                    logger.e(e) { "Error fetching NFTs from network ${e.cause}" }
                }
            }
        }

    fun getWalletStreamTokens(): Flow<List<NFTTrack>> = combine(
        getWalletNFTsCache(),
        policyIdsRepository.getPolicyIds()
    ) { walletNFTs, policyIds ->
        walletNFTs.filter { track ->
            track.policyId in policyIds
        }
    }

    fun deleteAllTracksNFTsCache() {
        db.deleteAllNFTs()
    }


    fun getWalletConnectionsCache(): Flow<List<WalletConnection>> {
        return db.getWalletConnections()
    }

    suspend fun getWalletConnectionsNetwork(): List<WalletConnection> {
        return try {
            val connections = walletConnectionAPI.getWalletConnections()
            db.cacheWalletConnections(connections)
            connections
        } catch (e: Exception) {
            logger.e(e) { "Error fetching wallet connections from network ${e.cause}" }
            throw e
        }
    }

    private fun getWalletNFTsCache(): Flow<List<NFTTrack>> = db.getAllTracks()

    suspend fun getWalletNFTsNetwork(): List<NFTTrack> {
        return try {
            service.getWalletNFTs()
        } catch (e: Exception) {
            logger.e(e) { "Error fetching NFTs from network ${e.cause}" }
            throw e
        }
    }



    fun connectWallet(newmCode: String) {
        scope.launch {
            val newConnection = walletConnectionAPI.connectWallet(newmCode.removePrefix("newm-"))
            db.cacheWalletConnections(listOf(newConnection))
        }
    }

    suspend fun disconnectWallet(walletConnectionId: String) {
        walletConnectionAPI.disconnectWallet(walletConnectionId)
        db.deleteAllWalletConnections(walletConnectionId)
    }
}
