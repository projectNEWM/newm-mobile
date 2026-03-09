package io.newm.shared

import io.newm.shared.commonPublic.models.CardanoChainMetadata
import io.newm.shared.commonPublic.models.ChainType
import io.newm.shared.commonPublic.models.EthereumChainMetadata
import io.newm.shared.commonPublic.models.NFTAllocation
import io.newm.shared.commonPublic.models.NFTTrack
import io.newm.shared.di.createJson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class NFTAllocationSerializationTest {
    private val json = createJson()

    @Test
    fun testSingleNFTAllocationSerialization() {
        val walletId = "single_wallet_id"
        val allocation = NFTAllocation(amount = 5, id = "single_allocation_id", walletId = walletId)

        val jsonString = json.encodeToString(allocation)
        val deserializedAllocation = json.decodeFromString<NFTAllocation>(jsonString)

        assertNotNull(deserializedAllocation)
        assertEquals(allocation.id, deserializedAllocation.id)
        assertEquals(allocation.amount, deserializedAllocation.amount)
        assertEquals(allocation.walletId, deserializedAllocation.walletId)
    }

    @Test
    fun testNFTAllocationWithWalletId_serialization() {
        val walletId1 = "wallet_id_1"
        val walletId2 = "wallet_id_2"
        val nftId = "nft_id_1"

        val allocations =
            listOf(
                NFTAllocation(amount = 1, id = "allocation_id_1", walletId = walletId1),
                NFTAllocation(amount = 2, id = "allocation_id_2", walletId = walletId2),
            )

        val nftTrack =
            NFTTrack(
                id = nftId,
                title = "Title 1",
                imageUrl = "image_url_1",
                audioUrl = "audio_url_1",
                duration = 180L,
                artists = listOf("Artist 1"),
                genres = listOf("Pop"),
                moods = listOf("Happy"),
                amount = 10L,
                chainType = ChainType.Cardano,
                chainMetadata =
                    CardanoChainMetadata("fingerprint_1", "policy_id_1", "asset_name_1", true),
                allocations = allocations,
            )

        val jsonString = json.encodeToString(nftTrack.allocations)
        val deserializedAllocations = json.decodeFromString<List<NFTAllocation>>(jsonString)

        assertEquals(2, deserializedAllocations.size)
        assertEquals(walletId1, deserializedAllocations[0].walletId)
        assertEquals(walletId2, deserializedAllocations[1].walletId)
    }

    @Test
    fun testNFTTrackSerializationAndDeserialization() {
        val nftId = "nft_id_2"
        val walletId = "some_wallet_id"

        val allocations =
            listOf(NFTAllocation(amount = 3, id = "allocation_id_3", walletId = walletId))

        val nftTrack =
            NFTTrack(
                id = nftId,
                title = "Title 2",
                imageUrl = "image_url_2",
                audioUrl = "audio_url_2",
                duration = 200L,
                artists = listOf("Artist 2"),
                genres = listOf("Rock"),
                moods = listOf("Energetic"),
                amount = 15L,
                chainType = ChainType.Ethereum,
                chainMetadata =
                    EthereumChainMetadata("contract_address_1", "ERC-721", "token_id_1"),
                allocations = allocations,
            )

        val jsonString = json.encodeToString(nftTrack)
        val deserializedNftTrack = json.decodeFromString<NFTTrack>(jsonString)

        assertNotNull(deserializedNftTrack)
        assertEquals(nftTrack.id, deserializedNftTrack.id)
        assertEquals(nftTrack.title, deserializedNftTrack.title)
        assertEquals(nftTrack.allocations.size, deserializedNftTrack.allocations.size)
        assertEquals(walletId, deserializedNftTrack.allocations[0].walletId)
    }
}
