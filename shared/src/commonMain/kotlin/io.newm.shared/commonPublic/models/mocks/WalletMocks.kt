package io.newm.shared.commonPublic.models.mocks

import io.newm.shared.commonPublic.models.WalletConnection

val EmptyWallet = WalletConnection(id = "EMPTY", createdAt = "EMPTY", stakeAddress = "EMPTY")

val ErrorWallet = WalletConnection(id = "ERROR", createdAt = "ERROR", stakeAddress = "ERROR")
