package io.newm.shared.internal.store

import io.newm.shared.internal.services.cache.NFTCacheService
import io.newm.shared.internal.services.network.NFTNetworkService
import io.newm.shared.public.models.NFTTrack
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder

internal class NftTrackStore(
    private val networkService: NFTNetworkService,
    private val cacheService: NFTCacheService,
) : Store<Unit, List<NFTTrack>> by StoreBuilder.from(
    fetcher = Fetcher.of { _: Unit ->
        networkService.getWalletNFTs()
    },
    sourceOfTruth = SourceOfTruth.of(
        reader = { _: Unit ->
            cacheService.getAllTracks()
        },
        writer = { _: Unit, tracks: List<NFTTrack> -> cacheService.cacheNFTTracks(tracks) },
        delete = { _: Unit ->
            cacheService.deleteAllNFTs()
        },
        deleteAll = cacheService::deleteAllNFTs
    )
).build()

