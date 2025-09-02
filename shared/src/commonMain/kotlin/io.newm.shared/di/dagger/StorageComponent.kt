package io.newm.shared.di.dagger

import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.shared.config.NewmSharedBuildConfigImpl
import io.newm.shared.commonInternal.db.PreferencesDataStore
import io.newm.shared.commonInternal.services.cache.NFTCacheService
import io.newm.shared.commonInternal.services.network.NFTNetworkService
import io.newm.shared.commonInternal.store.NftTrackStore
import me.tatarka.inject.annotations.Provides

interface StorageComponent {
    @Provides
    fun providesNftTrackStore(service: NFTNetworkService, cache: NFTCacheService): NftTrackStore {
        return NftTrackStore(service, cache)
    }

    @Provides
    fun providesNewmSharedBuildConfig(storage: PreferencesDataStore): NewmSharedBuildConfig {
        return NewmSharedBuildConfigImpl(storage)
    }
}