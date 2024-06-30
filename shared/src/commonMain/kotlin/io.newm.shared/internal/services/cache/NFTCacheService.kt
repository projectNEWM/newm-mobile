package io.newm.shared.internal.services.cache

import io.newm.shared.database.ENABLE_ROOM_DATABASE
import io.newm.shared.database.NewmAppDatabase
import io.newm.shared.database.entries.toDBNFTTrack
import io.newm.shared.database.entries.toNFTTrack
import io.newm.shared.internal.services.db.NewmDatabaseWrapper
import io.newm.shared.internal.services.db.cacheNFTTracks
import io.newm.shared.internal.services.db.deleteAllNFTs
import io.newm.shared.internal.services.db.getAllTracks
import io.newm.shared.public.models.NFTTrack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

internal class NFTCacheService(
    private val db: NewmDatabaseWrapper,
    private val database: NewmAppDatabase
)  {
    fun getAllTracks(): Flow<List<NFTTrack>> {
        return if(ENABLE_ROOM_DATABASE) {
            database.nftTracksDao().getAll().map { list ->
                list.map {
                    it.toNFTTrack()
                }
            }
        } else {
            db.getAllTracks()
        }
    }

    suspend fun cacheNFTTracks(tracks: List<NFTTrack>)  {
        if(ENABLE_ROOM_DATABASE) {
            database.nftTracksDao().insertAll(tracks.map { it.toDBNFTTrack() })
        } else {
            db.cacheNFTTracks(tracks)
        }
    }

    suspend fun deleteAllNFTs() {
        if (ENABLE_ROOM_DATABASE) {
            database.nftTracksDao().deleteAll()
        } else {
            db.deleteAllNFTs()
        }
    }

    fun getTrack(id: String): NFTTrack? {
        return if (ENABLE_ROOM_DATABASE) {
            runBlocking {
                database.nftTracksDao().getByIdFlow(id).firstOrNull()?.toNFTTrack()
            }
        } else {
            db.getTrack(id)
        }
    }
}
