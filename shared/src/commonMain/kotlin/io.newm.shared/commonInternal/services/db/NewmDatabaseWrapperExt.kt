package io.newm.shared.commonInternal.services.db

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import io.newm.shared.commonPublic.models.CardanoChainMetadata
import io.newm.shared.commonPublic.models.ChainType
import io.newm.shared.commonPublic.models.EthereumChainMetadata
import io.newm.shared.commonPublic.models.NFTAllocation
import io.newm.shared.commonPublic.models.NFTTrack
import io.newm.shared.commonPublic.models.WalletConnection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val TAG = "NFTDatabaseExt"

fun NewmDatabaseWrapper.getAllTracks(): Flow<List<NFTTrack>> =
    invoke()
        .nFTTrackQueries
        .selectAllTracks()
        .asFlow()
        .mapToList(kotlinx.coroutines.Dispatchers.Default)
        .map { tracksFromDb ->
            println("$TAG: Reading ${tracksFromDb.size} tracks from database")
            tracksFromDb.mapNotNull { track ->
                val chainType = ChainType.fromString(track.chainType)
                println(
                    "$TAG: Track '${track.title}' has chainType='${track.chainType}' -> parsed as $chainType",
                )
                val chainMetadata =
                    when (chainType) {
                        ChainType.Cardano -> {
                            CardanoChainMetadata(
                                fingerprint = track.fingerprint.orEmpty(),
                                policyId = track.policyId.orEmpty(),
                                assetName = track.assetName.orEmpty(),
                                isStreamToken = track.isStreamToken == 1L,
                            )
                        }

                        ChainType.Ethereum -> {
                            EthereumChainMetadata(
                                contractAddress = track.contractAddress.orEmpty(),
                                tokenType = track.tokenType.orEmpty(),
                                tokenId = track.tokenId.orEmpty(),
                            )
                        }

                        ChainType.Unknown -> {
                            // Skip tracks with unknown chain types
                            return@mapNotNull null
                        }
                    }
                val allocations: List<NFTAllocation> =
                    try {
                        Json.decodeFromString(track.allocations)
                    } catch (_: Exception) {
                        emptyList()
                    }
                NFTTrack(
                    id = track.id,
                    title = track.title,
                    amount = track.amount,
                    imageUrl = track.imageUrl,
                    audioUrl = track.audioUrl,
                    duration = track.duration,
                    artists = track.artists.split(","),
                    genres = track.genres.split(","),
                    moods = track.moods.split(","),
                    chainType = chainType,
                    chainMetadata = chainMetadata,
                    allocations = allocations,
                )
            }
        }

fun NewmDatabaseWrapper.cacheNFTTracks(nftTracks: List<NFTTrack>) {
    val ethereumCount = nftTracks.count { it.chainType == ChainType.Ethereum }
    val cardanoCount = nftTracks.count { it.chainType == ChainType.Cardano }
    println(
        "$TAG: Caching ${nftTracks.size} NFT tracks: $ethereumCount Ethereum, $cardanoCount Cardano",
    )
    invoke().transaction {
        nftTracks.forEach { track ->
            println(
                "$TAG: Caching track '${track.title}' with chainType=${track.chainType} (serialName='${track.chainType.serialName}')",
            )
            var policyId: String? = null
            var assetName: String? = null
            var fingerprint: String? = null
            var isStreamToken: Long? = null
            var contractAddress: String? = null
            var tokenType: String? = null
            var tokenId: String? = null

            when (track.chainMetadata) {
                is CardanoChainMetadata -> {
                    policyId = track.chainMetadata.policyId
                    assetName = track.chainMetadata.assetName
                    fingerprint = track.chainMetadata.fingerprint
                    isStreamToken = if (track.chainMetadata.isStreamToken) 1L else 0L
                }

                is EthereumChainMetadata -> {
                    contractAddress = track.chainMetadata.contractAddress
                    tokenType = track.chainMetadata.tokenType
                    tokenId = track.chainMetadata.tokenId
                }
            }
            invoke()
                .nFTTrackQueries
                .insertOrReplaceTrack(
                    id = track.id,
                    title = track.title,
                    amount = track.amount,
                    imageUrl = track.imageUrl,
                    audioUrl = track.audioUrl,
                    duration = track.duration,
                    artists = track.artists.joinToString(","),
                    genres = track.genres.joinToString(","),
                    moods = track.moods.joinToString(","),
                    chainType = track.chainType.serialName,
                    policyId = policyId,
                    assetName = assetName,
                    fingerprint = fingerprint,
                    isStreamToken = isStreamToken,
                    contractAddress = contractAddress,
                    tokenType = tokenType,
                    tokenId = tokenId,
                    allocations = Json.encodeToString(track.allocations),
                )
        }
    }
}

fun NewmDatabaseWrapper.deleteAllNFTs() {
    invoke().transaction { invoke().nFTTrackQueries.deleteAll() }
}

fun NewmDatabaseWrapper.findWalletConnectionByID(walletId: String): Flow<WalletConnection?> {
    return invoke()
        .walletConnectionQueries
        .findWalletConnectionById(walletId)
        .asFlow()
        .mapToOneOrNull(kotlinx.coroutines.Dispatchers.Default)
        .map { db ->
            if (db == null) return@map null
            WalletConnection(
                id = db.id,
                createdAt = db.createdAt,
                address = db.address,
                chain = ChainType.fromString(db.chain),
                name = db.name,
            )
        }
}

fun NewmDatabaseWrapper.getWalletConnections(): Flow<List<WalletConnection>> =
    invoke()
        .walletConnectionQueries
        .getAll()
        .asFlow()
        .mapToList(kotlinx.coroutines.Dispatchers.Default)
        .map { dbWalletConnections ->
            dbWalletConnections.map { wallet ->
                WalletConnection(
                    id = wallet.id,
                    createdAt = wallet.createdAt,
                    address = wallet.address,
                    chain = ChainType.fromString(wallet.chain),
                    name = wallet.name,
                )
            }
        }

fun NewmDatabaseWrapper.cacheWalletConnections(walletConnections: List<WalletConnection>) {
    invoke().transaction {
        walletConnections.forEach { connection ->
            invoke()
                .walletConnectionQueries
                .insert(
                    id = connection.id,
                    createdAt = connection.createdAt,
                    address = connection.address,
                    chain = connection.chain.serialName,
                    name = connection.name,
                )
        }
    }
}

fun NewmDatabaseWrapper.deleteWalletConnectionById(walletConnectionsId: String) {
    invoke().transaction { invoke().walletConnectionQueries.deleteById(walletConnectionsId) }
}

fun NewmDatabaseWrapper.deleteAllWalletConnections() {
    invoke().transaction { invoke().walletConnectionQueries.deleteAll() }
}

fun NewmDatabaseWrapper.updateWalletConnectionName(
    connectionId: String,
    name: String,
) {
    invoke().transaction { invoke().walletConnectionQueries.updateNameById(name, connectionId) }
}
