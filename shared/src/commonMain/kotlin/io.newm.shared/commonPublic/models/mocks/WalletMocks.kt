package io.newm.shared.commonPublic.models.mocks

import io.newm.shared.commonPublic.models.ChainType
import io.newm.shared.commonPublic.models.WalletConnection

val EmptyWallet =
    WalletConnection(
        id = "EMPTY",
        createdAt = "EMPTY",
        address = "EMPTY",
        chain = ChainType.Cardano,
        name = "Cardano Wallet Name",
    )

val ErrorWallet =
    WalletConnection(
        id = "ERROR",
        createdAt = "ERROR",
        address = "ERROR",
        chain = ChainType.Cardano,
        name = "Cardano Wallet Name",
    )
