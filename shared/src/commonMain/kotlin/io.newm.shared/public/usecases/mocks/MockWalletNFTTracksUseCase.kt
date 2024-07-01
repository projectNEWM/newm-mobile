package io.newm.shared.public.usecases.mocks

import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.models.mocks.mockTracks
import io.newm.shared.public.usecases.WalletNFTTracksUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class MockWalletNFTTracksUseCase: WalletNFTTracksUseCase {

    override suspend fun refresh() {
        // no-op
    }

    private var _hasEmptyWallet = MutableStateFlow(false)

    override val hasEmptyWallet: Flow<Boolean>
        get() = _hasEmptyWallet.asStateFlow()

    override fun setHasEmptyWallet(hasEmptyWallet: Boolean) {
        _hasEmptyWallet.update {
            hasEmptyWallet
        }
    }

    override fun getAllCollectableTracksFlow(): Flow<List<NFTTrack>> {
        return flow { emit(mockTracks) }
    }

    override suspend fun getAllCollectableTracks(): List<NFTTrack> {
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

    override suspend fun getAllTracksFlow(): Flow<List<NFTTrack>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTracks(): List<NFTTrack> {
        TODO("Not yet implemented")
    }
}
