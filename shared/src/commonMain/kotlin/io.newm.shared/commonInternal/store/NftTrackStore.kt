package io.newm.shared.commonInternal.store

import io.newm.shared.commonInternal.services.cache.NFTCacheService
import io.newm.shared.commonInternal.services.network.NFTNetworkService
import io.newm.shared.commonPublic.models.NFTTrack
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.Validator

class NftTrackStore(
    private val networkService: NFTNetworkService,
    private val cacheService: NFTCacheService,
) : Store<Unit, List<NFTTrack>> by StoreBuilder
        .from(
            fetcher = Fetcher.of { _: Unit -> networkService.getWalletNFTs() },
            sourceOfTruth =
                SourceOfTruth.of(
                    reader = { _: Unit -> cacheService.getAllTracks() },
                    writer = { _: Unit, tracks: List<NFTTrack> -> cacheService.cacheNFTTracks(tracks) },
                    delete = { _: Unit -> cacheService.deleteAllNFTs() },
                    deleteAll = cacheService::deleteAllNFTs,
                ),
        ).validator(
            Validator.by { it.isNotEmpty() }, // If we have no data, we should always fetch
        ).build()
