package io.newm.shared.public.usecases.mocks

import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.models.mocks.mockTracks
import io.newm.shared.public.usecases.WalletNFTTracksUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockWalletNFTTracksUseCase: WalletNFTTracksUseCase {

    override suspend fun refresh() {
        // no-op
    }

    override fun getAllNFTTracksFlow(): Flow<List<NFTTrack>> {
        return flow { emit(mockTracks) }
    }

    override suspend fun getAllNFTTracks(): List<NFTTrack> {
        return mockTracks
    }

    override fun getAllStreamTokensFlow(): Flow<List<NFTTrack>> {
        return flow { emit(mockTracks) }
    }

    override suspend fun getAllStreamTokens(): List<NFTTrack> {
        return mockTracks
    }

    override fun getNFTTrack(id: String): NFTTrack? {
        return mockTracks.firstOrNull { it.id == id }
    }
}
