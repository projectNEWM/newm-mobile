package io.newm.shared.commonInternal.repositories

import io.newm.shared.commonInternal.store.NftTrackStore
import io.newm.shared.commonPublic.models.CardanoChainMetadata
import io.newm.shared.commonPublic.models.ChainType
import io.newm.shared.commonPublic.models.NFTTrack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.core5.ExperimentalStoreApi
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import org.mobilenativefoundation.store.store5.impl.extensions.get

private const val TAG = "NFTRepository"

internal class NFTRepository(
    private val nftStore: NftTrackStore,
) {
    // TODO remove this when we start returning the state of the store
    private val _isSynced = MutableStateFlow(false)

    val isSynced: Flow<Boolean>
        get() = _isSynced.asStateFlow()

    suspend fun syncNFTTracksFromNetworkToDevice() {
        nftStore
            .stream(StoreReadRequest.fresh(Unit))
            .filterNot { it is StoreReadResponse.Loading }
            .first()

        _isSynced.value = true
    }

    suspend fun getAllCollectableTracks(): List<NFTTrack> =
        nftStore
            .get(Unit)
            .filterNot {
                val metadata = it.chainMetadata
                metadata is CardanoChainMetadata && metadata.isStreamToken
            }.sortedByChainType()

    suspend fun getAllStreamTokens(): List<NFTTrack> =
        nftStore.get(Unit).filter {
            val metadata = it.chainMetadata
            metadata is CardanoChainMetadata && metadata.isStreamToken
        }

    fun getAllCollectableTracksFlow(): Flow<List<NFTTrack>> =
        getAll().map { tracks ->
            val filtered =
                tracks.filter {
                    val metadata = it.chainMetadata
                    !(metadata is CardanoChainMetadata && metadata.isStreamToken)
                }
            val sorted = filtered.sortedByChainType()
            val ethereumCount = sorted.count { it.chainType == ChainType.Ethereum }
            val cardanoCount = sorted.count { it.chainType == ChainType.Cardano }
            println(
                "$TAG: getAllCollectableTracksFlow: ${sorted.size} tracks ($ethereumCount Ethereum, $cardanoCount Cardano)",
            )
            sorted
        }

    fun getAllStreamTokensFlow(): Flow<List<NFTTrack>> =
        getAll().map { tracks ->
            tracks.filter {
                val metadata = it.chainMetadata
                metadata is CardanoChainMetadata && metadata.isStreamToken
            }
        }

    @OptIn(ExperimentalStoreApi::class)
    suspend fun deleteAllTracksNFTsCache() {
        nftStore.clear()
    }

    fun getAll(refresh: Boolean = false): Flow<List<NFTTrack>> =
        nftStore.stream(StoreReadRequest.cached(Unit, refresh)).map { result ->
            // TODO handle error, loading, etc
            result.dataOrNull() ?: emptyList()
        }
}

/** Sorts NFT tracks with Ethereum tracks first, then Cardano, then Unknown. */
private fun List<NFTTrack>.sortedByChainType(): List<NFTTrack> =
    sortedBy { track ->
        when (track.chainType) {
            ChainType.Ethereum -> 0
            ChainType.Cardano -> 1
            ChainType.Unknown -> 2
        }
    }
