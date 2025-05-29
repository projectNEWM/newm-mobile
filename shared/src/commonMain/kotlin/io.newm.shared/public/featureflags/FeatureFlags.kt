package io.newm.shared.public.featureflags

interface FeatureFlag {
    val key: String
}

object FeatureFlags {
    object AdvancedAccess : FeatureFlag {
        override val key: String
            get() = "mobile-app-advanced-access"
    }

    object ShowRecordStore : FeatureFlag {
        override val key = "mobile-app-show-recordstore"
    }

    object DownloadTracks : FeatureFlag {
        override val key = "mobile-app-track-downloads"
    }

    object ShowInvestmentPortfolio : FeatureFlag {
        override val key = "mobile-app-show-investments-portfolio"
    }

    object ShowMultiWallets : FeatureFlag {
        override val key = "mobile-app-show-connected-wallets-flow"
    }
}

