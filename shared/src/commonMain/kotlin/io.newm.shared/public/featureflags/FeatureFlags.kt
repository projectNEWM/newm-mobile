package io.newm.shared.public.featureflags

interface FeatureFlag {
    val key: String
    val displayName: String
    val defaultUiValue: Boolean
}

object FeatureFlags {
    object AdvancedAccess : FeatureFlag {
        override val key: String = "mobile-app-advanced-access"
        override val displayName = "Advanced Access (Global)"
        override val defaultUiValue = false
    }

    object ShowRecordStore : FeatureFlag {
        override val key = "mobile-app-show-recordstore"
        override val displayName = "Show Record Store"
        override val defaultUiValue = false
    }

    object DownloadTracks : FeatureFlag {
        override val key = "mobile-app-track-downloads"
        override val displayName = "Allow Track Downloads"
        override val defaultUiValue = true
    }

    object ShowInvestmentPortfolio : FeatureFlag {
        override val key = "mobile-app-show-investments-portfolio"
        override val displayName = "Show Investment Portfolio"
        override val defaultUiValue = false
    }

    object ShowMultiWallets : FeatureFlag {
        override val key = "mobile-app-show-connected-wallets-flow"
        override val displayName = "Show Multi-Wallet Flow"
        override val defaultUiValue = false
    }

    val ALL_FLAGS: List<FeatureFlag> by lazy {
        listOf(
            AdvancedAccess,
            ShowRecordStore,
            DownloadTracks,
            ShowInvestmentPortfolio,
            ShowMultiWallets
        )
    }
}

