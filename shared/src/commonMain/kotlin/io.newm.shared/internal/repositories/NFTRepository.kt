package io.newm.shared.internal.repositories

import io.newm.shared.NewmAppLogger
import io.newm.shared.internal.services.cache.NFTCacheService
import io.newm.shared.internal.services.network.NFTNetworkService
import io.newm.shared.public.models.NFTTrack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

internal class NFTRepository(
    private val networkService: NFTNetworkService,
    private val cacheService: NFTCacheService,
    private val logger: NewmAppLogger
) {

    private val _syncedNftWallet = MutableStateFlow(false)

    val isSynced: Flow<Boolean>
        get() = _syncedNftWallet.asStateFlow()

    suspend fun syncNFTTracksFromNetworkToDevice(): List<NFTTrack>? {
        return try {
            _syncedNftWallet.update { false }
            val nfts = networkService.getWalletNFTs()
            cacheService.cacheNFTTracks(nfts)
            _syncedNftWallet.update { true }
            nfts
        } catch (e: Exception) {
            logger.error("NFTRepository", "Error fetching NFTs from network ${e.cause}", e)
            null
        }
    }

    fun getAllCollectableTracksFlow(): Flow<List<NFTTrack>> {
        return cacheService.getAllTracks()
            .map { tracks -> tracks.filter { !it.isStreamToken } }
    }

    fun getAllStreamTokensFlow(): Flow<List<NFTTrack>> {
        return cacheService.getAllTracks()
            .map { tracks -> tracks.filter { it.isStreamToken } }
    }

    fun deleteAllTracksNFTsCache() {
        cacheService.deleteAllNFTs()
    }

    fun getTrack(id: String): NFTTrack? {
        return cacheService.getTrack(id)
    }

    fun getAll(): Flow<List<NFTTrack>> =
        cacheService.getAllTracks()
}
