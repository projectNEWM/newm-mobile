package io.newm.shared.commonPublic.usecases.mocks

import io.newm.shared.commonPublic.models.NFTTrack
import io.newm.shared.commonPublic.models.mocks.mockTracks
import io.newm.shared.commonPublic.usecases.WalletNFTTracksUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

class MockWalletNFTTracksUseCase : WalletNFTTracksUseCase {
    override suspend fun refresh() {
        // no-op
    }

    private var _walletSynced = MutableStateFlow(false)

    override val walletSynced: Flow<Boolean>
        get() = _walletSynced.asStateFlow()

    override fun getAllCollectableTracksFlow(): Flow<List<NFTTrack>> = flow { emit(mockTracks) }

    override suspend fun getAllCollectableTracks(): List<NFTTrack> = mockTracks

    override fun getAllStreamTokensFlow(): Flow<List<NFTTrack>> = flow { emit(mockTracks) }

    override suspend fun getAllStreamTokens(): List<NFTTrack> = mockTracks

    override suspend fun getAllTracksFlow(): Flow<List<NFTTrack>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTracks(): List<NFTTrack> {
        TODO("Not yet implemented")
    }
}
