package io.newm.shared.commonInternal.services.cache

import io.newm.shared.commonInternal.services.db.NewmDatabaseWrapper
import io.newm.shared.commonInternal.services.db.cacheNFTTracks
import io.newm.shared.commonInternal.services.db.deleteAllNFTs
import io.newm.shared.commonInternal.services.db.getAllTracks
import io.newm.shared.commonPublic.models.NFTTrack
import kotlinx.coroutines.flow.Flow

class NFTCacheService(
    private val db: NewmDatabaseWrapper
)  {
    fun getAllTracks(): Flow<List<NFTTrack>> = db.getAllTracks()

    fun cacheNFTTracks(nfts: List<NFTTrack>) = db.cacheNFTTracks(nfts)

    fun deleteAllNFTs() = db.deleteAllNFTs()
}
