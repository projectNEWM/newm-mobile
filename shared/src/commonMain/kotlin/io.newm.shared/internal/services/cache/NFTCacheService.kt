package io.newm.shared.internal.services.cache

import io.newm.shared.internal.services.db.NewmDatabaseWrapper
import io.newm.shared.internal.services.db.cacheNFTTracks
import io.newm.shared.internal.services.db.deleteAllNFTs
import io.newm.shared.internal.services.db.getAllTracks
import io.newm.shared.public.models.NFTTrack
import kotlinx.coroutines.flow.Flow

internal class NFTCacheService(
    private val db: NewmDatabaseWrapper
)  {
    fun getAllTracks(): Flow<List<NFTTrack>> = db.getAllTracks()

    fun cacheNFTTracks(nfts: List<NFTTrack>) = db.cacheNFTTracks(nfts)

    fun deleteAllNFTs() = db.deleteAllNFTs()
}
