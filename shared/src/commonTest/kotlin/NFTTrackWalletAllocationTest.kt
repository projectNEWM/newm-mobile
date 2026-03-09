package io.newm.shared

import io.newm.shared.commonPublic.models.CardanoChainMetadata
import io.newm.shared.commonPublic.models.ChainType
import io.newm.shared.commonPublic.models.NFTAllocation
import io.newm.shared.commonPublic.models.NFTTrack
import io.newm.shared.commonPublic.models.hasAllocationForWallet
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NFTTrackWalletAllocationTest {
    @Test
    fun `hasAllocationForWallet matches allocation id as wallet connection id`() {
        val track =
            NFTTrack(
                id = "track-1",
                title = "Track",
                imageUrl = "image",
                audioUrl = "audio",
                duration = 180L,
                artists = listOf("Artist"),
                genres = listOf("Genre"),
                moods = listOf("Mood"),
                amount = 1L,
                chainType = ChainType.Cardano,
                chainMetadata =
                    CardanoChainMetadata(
                        fingerprint = "fingerprint",
                        policyId = "policy-id",
                        assetName = "asset-name",
                        isStreamToken = false,
                    ),
                allocations = listOf(NFTAllocation(id = "wallet-123", amount = 1L)),
            )

        assertTrue(track.hasAllocationForWallet("wallet-123"))
        assertFalse(track.hasAllocationForWallet("wallet-999"))
    }
}
