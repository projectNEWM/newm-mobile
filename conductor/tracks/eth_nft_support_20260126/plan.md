# Plan: Update app to support ETH NFTs

This track covers the necessary changes to support ETH NFTs and the related backend updates.

## Subtasks

- Add Ktor decompression plugin.
- Update WalletConnection data structure to use `address` instead of `stakeAddress`.
- Update NFT data structure to support multi-chain NFTs.
- Update API endpoint for fetching NFT songs to `/v1/nft/songs`.
