package io.newm.shared.internal.repositories.testdata

import io.newm.shared.public.models.NFTTrack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal object MockSongs {

    fun getSongsFlow(): Flow<List<NFTTrack>> = flow {
        emit(
            savedSongModels.mapIndexed { index, mockSongModel ->
                mockSongModel.toMockSong(index.toString())
            }
        )
    }

    private fun MockSongModel.toMockSong(index: String): NFTTrack {
        return createSong(
            id = index,
            title = title,
            coverArtUrl = imageUrl,
        )
    }

    private fun createSong(
        id: String,
        title: String,
        coverArtUrl: String,
    ): NFTTrack = NFTTrack(
        id = id,
        name = title,
        imageUrl = coverArtUrl,
        songUrl = "https://ipfs.poolpm.nftcdn.io/ipfs/QmW6zkt82o4BbPh87uYYUSNbSSfmy6wbLCd2NE7VQk83Vn?tk=UmgSevoPuMvI0Hd6xQdzs_DA7ExwXGwSp7Yr_5xTUOU",
        artists = listOf("{insert artist name}"),
        duration = 2L,
    )
}

val savedSongModels = listOf(
    // Actual Fake Songs
    MockSongModel(
        title = "Letting My Hair Down",
        artist = "Reese Goodheart",
        imageUrl = "https://ipfs.poolpm.nftcdn.io/ipfs/QmcF3h9q4fcozK7bkyy7aF1rXn4UgRb1RsuXiKSwqpnkvU?tk=3FdVSIK8a11t8jTPMSeM9BCkgrBLjVIEJxwX0kehmGg",
        assetId = 1
    ),
    MockSongModel(
        title = "California Throwback",
        artist = "Jayy Gawd",
        imageUrl = "https://ipfs.poolpm.nftcdn.io/ipfs/QmaPW9RknVe5zK56xnN8VeU1hubuzQPiv39Sv9mSjNNWmd?tk=0eQNLjTAs95lFEUuscQvHlDUofqZRc2CbhEoOB6rRHU",
        assetId = 2
    ),
    MockSongModel(
        title = "It's All About Love",
        artist = "Mike Lerman",
        imageUrl = "https://ipfs.poolpm.nftcdn.io/ipfs/QmW6zkt82o4BbPh87uYYUSNbSSfmy6wbLCd2NE7VQk83Vn?tk=UmgSevoPuMvI0Hd6xQdzs_DA7ExwXGwSp7Yr_5xTUOU",
        assetId = 3
    ),
    MockSongModel(
        title = "Don't You Know",
        artist = "Corey Drumz",
        imageUrl = "https://ipfs.poolpm.nftcdn.io/ipfs/QmRtJVVqbrBhgvWXcbFdAN89uZ4QG4FYbYVtPjSu6TifB3?tk=gNH23mDQ4hAEc8rAWY_yhTLHU2sbd6S80GRBOb-9R2w",
        assetId = 4
    ),
    MockSongModel(
        title = "Get Down",
        artist = "Dame Jazz",
        imageUrl = "https://ipfs.poolpm.nftcdn.io/ipfs/QmNowiyceACR2zbTAhjqxWNByT2tUcgRFgk6HEfRj5qLq2?tk=IJXVDbNjhGoQKgzqweEpE83O-3jChASlc_DylcjgvT0",
        assetId = 5
    ),
    MockSongModel(
        title = "Authentic",
        artist = "Solo The Beatman",
        imageUrl = "https://ipfs.poolpm.nftcdn.io/ipfs/QmbipqRVkxFP3DgVGvAAGj9ZsCbcfWatPSEmbejpNH5c5K?tk=8OIr3-ZF2SnfjysitvWLePy2ljE3eOiP6ul95abWVWA",
        assetId = 6
    ),
)

data class MockSongModel(
    val title: String,
    val artist: String,
    val imageUrl: String,
    val assetId: Int = 0
)