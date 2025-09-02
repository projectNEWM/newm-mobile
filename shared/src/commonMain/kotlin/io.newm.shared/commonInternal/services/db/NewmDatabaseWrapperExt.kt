package io.newm.shared.commonInternal.services.db

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import io.newm.shared.commonPublic.models.NFTTrack
import io.newm.shared.commonPublic.models.WalletConnection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun NewmDatabaseWrapper.getAllTracks(): Flow<List<NFTTrack>> =
    invoke().nFTTrackQueries.selectAllTracks()
        .asFlow()
        .mapToList()
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
                    moods = track.genres.split(","),
                    isStreamToken = track.isStreamToken == 1L,
                )
            }
        }

fun NewmDatabaseWrapper.cacheNFTTracks(nftTracks: List<NFTTrack>) {
    invoke().transaction {
        nftTracks.forEach { track ->
            invoke().nFTTrackQueries.insertOrReplaceTrack(
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
                moods = track.moods.joinToString(","),
                isStreamToken = if (track.isStreamToken) 1L else 0L
            )
        }
    }
}

fun NewmDatabaseWrapper.deleteAllNFTs() {
    invoke().transaction {
        invoke().nFTTrackQueries.deleteAll()
    }
}

fun NewmDatabaseWrapper.findWalletConnectionByID(walletId: String): Flow<WalletConnection?> {
    return invoke().walletConnectionQueries.findWalletConnectionById(walletId)
        .asFlow()
        .mapToOneOrNull()
        .map { db ->
            if (db == null) return@map null
            WalletConnection(
                id = db.id,
                createdAt = db.createdAt,
                stakeAddress = db.stakeAddress
            )
        }
}

fun NewmDatabaseWrapper.getWalletConnections(): Flow<List<WalletConnection>> =
    invoke().walletConnectionQueries.getAll()
        .asFlow()
        .mapToList()
        .map { dbWalletConnections ->
            dbWalletConnections.map { wallet ->
                WalletConnection(
                    id = wallet.id,
                    createdAt = wallet.createdAt,
                    stakeAddress = wallet.stakeAddress
                )
            }
        }


fun NewmDatabaseWrapper.cacheWalletConnections(walletConnections: List<WalletConnection>) {
    invoke().transaction {
        walletConnections.forEach { connection ->
            invoke().walletConnectionQueries.insert(
                id = connection.id,
                createdAt = connection.createdAt,
                stakeAddress = connection.stakeAddress
            )
        }
    }
}

fun NewmDatabaseWrapper.deleteWalletConnectionById(walletConnectionsId: String) {
    invoke().transaction {
        invoke().walletConnectionQueries.deleteById(walletConnectionsId)
    }
}

fun NewmDatabaseWrapper.deleteAllWalletConnections() {
    invoke().transaction {
        invoke().walletConnectionQueries.deleteAll()
    }
}
