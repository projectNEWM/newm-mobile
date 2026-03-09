package io.newm.shared

import io.newm.shared.commonInternal.api.models.CardanoNFTChainMetadataResponse
import io.newm.shared.commonInternal.api.models.EthereumNFTChainMetadataResponse
import io.newm.shared.commonInternal.api.models.NFTSongResponse
import io.newm.shared.commonInternal.api.models.toDomainOrNull
import io.newm.shared.commonPublic.models.CardanoChainMetadata
import io.newm.shared.commonPublic.models.ChainType
import io.newm.shared.commonPublic.models.EthereumChainMetadata
import io.newm.shared.di.createJson
import kotlinx.serialization.decodeFromString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class NFTSongResponseDecodingTest {
    @Test
    fun `decode wiki response shape into domain nft tracks`() {
        val payload =
            """
            [
              {
                "id": "cf42d678-e7e7-3986-9cca-3f2d42461c5f",
                "title": "Daisuke",
                "imageUrl": "https://example.com/cardano.png",
                "audioUrl": "https://example.com/cardano.mp3",
                "duration": 200,
                "artists": ["Danketsu", "Mirai Music", "NSTASIA"],
                "genres": ["Pop", "House", "Tribal"],
                "moods": ["Spiritual"],
                "amount": 1000000,
                "allocations": [
                  {
                    "id": "170c0a9b-0216-4429-b8a5-15a094dd2e38",
                    "amount": 400000
                  }
                ],
                "chainMetadata": {
                  "chain": "Cardano",
                  "fingerprint": "asset1effvlkkw02m9ft3ymlkfld8mhlq05wc2hal5du",
                  "policyId": "46e607b3046a34c95e7c29e47047618dbf5e10de777ba56c590cfd5c",
                  "assetName": "NEWM_5",
                  "isStreamToken": true
                }
              },
              {
                "id": "4faf291f-060d-318a-a1d9-3e64288127c4",
                "title": "A Little Rain Must Fall",
                "imageUrl": "https://example.com/ethereum.png",
                "audioUrl": "https://example.com/ethereum.mp3",
                "duration": -1,
                "artists": ["Violetta Zironi"],
                "genres": [],
                "moods": [],
                "amount": 1,
                "allocations": [
                  {
                    "id": "f524574f-4377-48da-80eb-f116d0d05c76",
                    "amount": 1
                  }
                ],
                "chainMetadata": {
                  "chain": "Ethereum",
                  "contractAddress": "0x328B49C56a8A15fb34aB3eCD8883Fac5F9512453",
                  "tokenType": "ERC721",
                  "tokenId": "107"
                }
              }
            ]
            """.trimIndent()

        val responses = createJson().decodeFromString<List<NFTSongResponse>>(payload)
        val tracks = responses.mapNotNull(NFTSongResponse::toDomainOrNull)

        assertEquals(2, tracks.size)
        assertEquals(ChainType.Cardano, tracks[0].chainType)
        assertEquals("170c0a9b-0216-4429-b8a5-15a094dd2e38", tracks[0].allocations.single().id)
        assertEquals(ChainType.Ethereum, tracks[1].chainType)
        assertEquals("f524574f-4377-48da-80eb-f116d0d05c76", tracks[1].allocations.single().id)

        assertIs<CardanoNFTChainMetadataResponse>(responses[0].chainMetadata)
        assertIs<EthereumNFTChainMetadataResponse>(responses[1].chainMetadata)
        assertIs<CardanoChainMetadata>(tracks[0].chainMetadata)
        assertIs<EthereumChainMetadata>(tracks[1].chainMetadata)
    }
}
