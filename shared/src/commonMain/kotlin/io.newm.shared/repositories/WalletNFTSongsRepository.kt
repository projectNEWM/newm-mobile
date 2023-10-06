package io.newm.shared.repositories

import co.touchlab.kermit.Logger
import io.newm.shared.models.Song
import io.newm.shared.repositories.testdata.MockSongs
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

internal class WalletNFTSongsRepository() : KoinComponent {

    private val logger = Logger.withTag("NewmKMM-WalletNFTSongsRepository")

    init {
        logger.d { "init" }
    }

    @Suppress("UNUSED_PARAMETER")
    fun getAllWalletNFTSongs(xPub: String): Flow<List<Song>> = MockSongs.getSongsFlow()
}