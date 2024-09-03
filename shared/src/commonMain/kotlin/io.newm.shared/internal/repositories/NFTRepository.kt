package io.newm.shared.internal.repositories

import io.newm.shared.internal.store.NftTrackStore
import io.newm.shared.public.models.NFTTrack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.core5.ExperimentalStoreApi
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.impl.extensions.fresh

internal class NFTRepository(
    private val nftStore: NftTrackStore
) {

    // TODO remove this when we start returning the state of the store
    private val _syncedNftWallet = MutableStateFlow(false)

    val isSynced: Flow<Boolean>
        get() = _syncedNftWallet.asStateFlow()

    suspend fun syncNFTTracksFromNetworkToDevice(): List<NFTTrack> {
        return nftStore.fresh(Unit).also {
            _syncedNftWallet.value = true
        }
    }

    fun getAllCollectableTracksFlow(): Flow<List<NFTTrack>> = getAll().map { tracks ->
        tracks.filter { !it.isStreamToken }
    }

    fun getAllStreamTokensFlow(): Flow<List<NFTTrack>> = getAll().map { tracks ->
        tracks.filter { it.isStreamToken }
    }

    @OptIn(ExperimentalStoreApi::class)
    suspend fun deleteAllTracksNFTsCache() {
        nftStore.clear()
    }

    fun getAll(refresh: Boolean = false): Flow<List<NFTTrack>> =
        nftStore.stream(StoreReadRequest.cached(Unit, refresh))
            .map { result ->
                // TODO handle error, loading, etc
                result.dataOrNull() ?: emptyList()
            }
}


