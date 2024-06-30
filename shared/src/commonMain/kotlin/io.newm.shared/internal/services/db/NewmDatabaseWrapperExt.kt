package io.newm.shared.internal.services.db

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.models.WalletConnection
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

fun NewmDatabaseWrapper.getWalletConnections(): Flow<List<WalletConnection>> =
    invoke().walletConnectionQueries.getAll()
        .asFlow()
        .mapToList()
        .map { dbWalletConnections ->
            dbWalletConnections.map { wallet ->
                WalletConnection(
                    id = wallet.id,
                    createdAt = wallet.createdAt,
                    stakeAddress = wallet.stakeAddress,
                    name = ""
                )
            }
        }


fun NewmDatabaseWrapper.cacheWalletConnections(walletConnections: List<WalletConnection>) {
    invoke().transaction {
        walletConnections.forEach { connection ->
            invoke().walletConnectionQueries.insert(
                id = connection.id,
                createdAt = connection.createdAt,
                stakeAddress = connection.stakeAddress,
            )
        }
    }
}

fun NewmDatabaseWrapper.getWalletConnectionById(id: String): Flow<WalletConnection?> =
    invoke().walletConnectionQueries.getAll()
        .asFlow()
        .mapToOneOrNull()
        .map { wallet ->
            if (wallet != null) {
                WalletConnection(
                    id = wallet.id,
                    createdAt = wallet.createdAt,
                    stakeAddress = wallet.stakeAddress,
                    name = ""
                )
            } else {
                null
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
