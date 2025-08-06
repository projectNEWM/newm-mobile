package io.newm.shared.commonPublic.featureflags


interface FeatureFlag {
    val key: String
    val displayName: String
    val description: String
    val category: FlagCategory
    val defaultValue: Boolean
}

enum class FlagCategory {
    UI_FEATURE,
    BUSINESS_LOGIC,
    DEVELOPER_TOOLS
}

// Type-safe flag definitions with rich metadata
object FeatureFlags {

    object ShowRecordStore : FeatureFlag {
        override val key = "mobile-app-show-recordstore"
        override val displayName = "Record Store"
        override val description = "Display the music record store interface"
        override val category = FlagCategory.UI_FEATURE
        override val defaultValue = false
    }

    object DownloadTracks : FeatureFlag {
        override val key = "mobile-app-track-downloads"
        override val displayName = "Track Downloads"
        override val description = "Allow users to download tracks for offline playback"
        override val category = FlagCategory.BUSINESS_LOGIC
        override val defaultValue = true
    }

    object ShowInvestmentPortfolio : FeatureFlag {
        override val key = "mobile-app-show-investments-portfolio"
        override val displayName = "Investment Portfolio"
        override val description = "Display investment portfolio and related features"
        override val category = FlagCategory.UI_FEATURE
        override val defaultValue = false
    }

    object ShowMultiWallets : FeatureFlag {
        override val key = "mobile-app-show-connected-wallets-flow"
        override val displayName = "Multi-Wallet Support"
        override val description = "Enable multiple wallet connection and management"
        override val category = FlagCategory.BUSINESS_LOGIC
        override val defaultValue = false
    }

    object ShowNEWMStudio : FeatureFlag {
        override val key = "mobile-app-show-newm-studio"
        override val displayName = "Artists Studio Access"
        override val description = "Enable access to NEWM Distribution Studio"
        override val category = FlagCategory.UI_FEATURE
        override val defaultValue = false
    }

    val ALL_FLAGS: List<FeatureFlag> = listOf(
        ShowRecordStore,
        DownloadTracks,
        ShowInvestmentPortfolio,
        ShowMultiWallets,
        ShowNEWMStudio
    )
}


