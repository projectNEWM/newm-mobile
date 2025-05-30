package io.newm.shared.public.models.mocks

import io.newm.shared.public.models.WalletConnection

val EmptyWallet = WalletConnection(
    id = "EMPTY",
    createdAt = "EMPTY",
    stakeAddress = "EMPTY"
)

val ErrorWallet = WalletConnection(
    id = "ERROR",
    createdAt = "ERROR",
    stakeAddress = "ERROR"
)