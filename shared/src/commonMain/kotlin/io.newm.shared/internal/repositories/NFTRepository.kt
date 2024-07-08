package io.newm.shared.internal.repositories

import io.newm.shared.NewmAppLogger
import io.newm.shared.internal.services.cache.NFTCacheService
import io.newm.shared.internal.services.network.NFTNetworkService
import io.newm.shared.public.models.NFTTrack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

internal class NFTRepository(
    private val networkService: NFTNetworkService,
    private val cacheService: NFTCacheService,
    private val policyIdsRepository: NewmPolicyIdsRepository,
    private val logger: NewmAppLogger
) {

    suspend fun syncNFTTracksFromNetworkToDevice(): List<NFTTrack>? {
        return try {
            val nfts = networkService.getWalletNFTs()
            cacheService.cacheNFTTracks(nfts)
            nfts
        } catch (e: Exception) {
            logger.error("NFTRepository", "Error fetching NFTs from network ${e.cause}", e)
            null
        }
    }

    fun getAllCollectableTracksFlow(): Flow<List<NFTTrack>> =
        combine(
            cacheService.getAllTracks(),
            policyIdsRepository.getPolicyIds()
        ) { walletNFTs, policyIds ->
            walletNFTs.filter { track -> track.policyId !in policyIds }
        }

    fun getAllStreamTokensFlow(): Flow<List<NFTTrack>> =
        combine(
            cacheService.getAllTracks(),
            policyIdsRepository.getPolicyIds()
        ) { walletNFTs, policyIds ->
            walletNFTs.filter { track -> track.policyId in policyIds }
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
