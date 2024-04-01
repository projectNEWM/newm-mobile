package io.newm.shared.public.models.mocks

import io.newm.shared.public.models.NFTTrack

val mockTracks: List<NFTTrack> = listOf(
    makeMockTrack(
        id = "0",
        name = "Dripdropz",
        imageUrl = "https://arweave.net/zBVmedCDTGBH06tTEA5u0aYFWkXPkr9w1GxGefxJIms",
        songUrl = "https://arweave.net/tWDP3Lr4U3vMy_iYwm-FPBa6ad0aaMNdwHa7MUAFjuo",
        duration = 30,
        artists = listOf("Esco")
    ),
    makeMockTrack(
        id = "1",
        name = "Lost In My Own Zone",
        imageUrl = "https://arweave.net/NkVFjDc24JfU4kgKE_vgz6hnuBpx_5ics2RTrrQEP6U",
        songUrl = "https://arweave.net/VkFPK-T7xkMgblDbxjfKjI7UXeyu23AOyg7pm4VjxzY",
        duration = 30,
        artists = listOf("Abyss")
    ),
    makeMockTrack(
        id = "2",
        name = "Sexiest Man Alive",
        imageUrl = "https://arweave.net/eYQ-pcX9qwh3KuVcdbClQQMkdgehula0ZhtSGomzdbg",
        songUrl = "https://arweave.net/KN9xqm4VroNdlgo30lA5hvkOPasgFIbSUqAVAPn8ob0",
        duration = 30,
        artists = listOf("Mike Lerman")
    ),
    makeMockTrack(
        id = "3",
        name = "Daisuke",
        imageUrl = "https://arweave.net/GlMlqHIPjwUtlPUfQxDdX1jWSjlKK1BCTBIekXgA66A",
        songUrl = "https://arweave.net/QpgjmWmAHNeRVgx_Ylwvh16i3aWd8BBgyq7f16gaUu0",
        duration = 30,
        artists = listOf("Danketsu", "Mirai Music", "NSTASIA")
    ),
    makeMockTrack(
        id = "4",
        name = "Love In The Water",
        imageUrl = "https://arweave.net/f5W8RZmAQimuz_vytFY9ofIzd9QpGaDIv2UXrrahTu4",
        songUrl = "https://arweave.net/DeVRF-RTkRRHoP4M-L9AjIu35ilzgclhLOrgQB2Px34",
        duration = 30,
        artists = listOf("NIDO")
    ),
    makeMockTrack(
        id = "5",
        name = "New Song",
        imageUrl = "https://arweave.net/xUauTN89ulvWAQ2Euz12ogF3EbDaiNPQKNe0I0Ib-mA",
        songUrl = "https://arweave.net/jeVMmGLmrtV3Dn-TfPkdCAn-Qjei4A2kFUOhFuvSCKU",
        duration = 30,
        artists = listOf("Esco")
    ),
    makeMockTrack(
        id = "6",
        name = "Bigger Dreams",
        imageUrl = "https://arweave.net/CuPFY2Ln7yUUhJX09G530kdPf93eGhAVlhjrtR7Jh5w",
        songUrl = "https://arweave.net/P141o0RDAjSYlVQgTDgHNAORQTkMYIVCprmD_dKMVss",
        duration = 30,
        artists = listOf("MURS")
    ),
    makeMockTrack(
        id = "7",
        name = "Space Cowboy",
        imageUrl = "https://arweave.net/qog8drrF9Oa55eWclrUejI65rn29gdcDX-Bj31VwBMc",
        songUrl = "https://arweave.net/W-PMgNX28f1RE1qwLG7SIU14-NPEmatFr51-zUAsqFI",
        duration = 30,
        artists = listOf("JUSE")
    ),
    makeMockTrack(
        id = "8",
        name = "Best Song Ever",
        imageUrl = "https://arweave.net/tlsj0fyL0BXU871-my1CvnNSBjMgXRn4zaO3taFVz3k",
        songUrl = "https://arweave.net/6HwXcWvOwfOWTljGYTlce0_zJjg1UslgkvNue6pau0E",
        duration = 30,
        artists = listOf("Esco")
    ),
    makeMockTrack(
        id = "9",
        name = "Underdog, Pt. 2",
        imageUrl = "https://arweave.net/eYQ-pcX9qwh3KuVcdbClQQMkdgehula0ZhtSGomzdbg",
        songUrl = "https://arweave.net/Em9XiS87I9ff8Wx2WOt2GIZ650gaiRTSjkNfJdvluLs",
        duration = 30,
        artists = listOf("Mike Lerman")
    ),
    makeMockTrack(
        id = "10",
        name = "Make It Easy",
        imageUrl = "https://arweave.net/eYQ-pcX9qwh3KuVcdbClQQMkdgehula0ZhtSGomzdbg",
        songUrl = "https://arweave.net/bWGg7d-GhU7Y3ZTp9zfpv1JYXlS7JTxqZriFHtAgLF4",
        duration = 30,
        artists = listOf("Mike Lerman")
    )
)

private fun makeMockTrack(
    id: String,
    name: String,
    imageUrl: String,
    songUrl: String,
    duration: Long,
    artists: List<String>
): NFTTrack {
    return NFTTrack(
		id = id,
		policyId = "",
		title = name,
		assetName = "",
		amount = 0,
		imageUrl = imageUrl,
		audioUrl = songUrl,
		duration = duration,
		artists = artists,
		genres = arrayOf("Jazz").asList(),
		moods = arrayOf("Rock").asList()
    )
}
