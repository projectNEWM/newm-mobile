package io.newm.shared.public.usecases

import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.models.error.KMMException
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

interface WalletNFTSongsUseCase {
    fun getAllWalletNFTSongs(): Flow<List<NFTTrack>>

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getWalletNFTs(): List<NFTTrack>
}

class WalletNFTSongsUseCaseProvider(): KoinComponent {
    private val walletNFTSongsUseCase: WalletNFTSongsUseCase by inject()

    fun get(): WalletNFTSongsUseCase {
        return this.walletNFTSongsUseCase
    }
}