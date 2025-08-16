package io.newm.screens

import io.newm.shared.public.analytics.events.AppScreens
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import com.slack.circuit.runtime.screen.Screen as CircuitScreen

@Parcelize
sealed class Screen(val screenName: String, val showBottomBar: Boolean = false, val showMiniPlayer: Boolean = false) : CircuitScreen {

    data object Wallets : Screen(screenName = AppScreens.WalletsScreen.name, showBottomBar = true, showMiniPlayer = true)
    data class WalletDetail(val walletId: String, val walletName: String) : Screen(screenName = AppScreens.WalletDetailScreen.name)
    data object UserAccount : Screen(screenName = AppScreens.AccountScreen.name, showBottomBar = true, showMiniPlayer = true)

    data object RecordStore : Screen(screenName = AppScreens.RecordStoreScreen.name, showBottomBar = true, showMiniPlayer = true)

    data object NFTLibrary : Screen(screenName = AppScreens.NFTLibraryScreen.name, showBottomBar = true, showMiniPlayer = true)

    data object InvestmentPortfolio : Screen(screenName = AppScreens.InvestmentPortfolioScreen.name, showBottomBar = true, showMiniPlayer = true)

    data object Studio : Screen(screenName = AppScreens.StudioScreen.name)

    data object Marketplace : Screen(screenName = AppScreens.MarketplaceScreen.name, showBottomBar = true, showMiniPlayer = true)

    data object Welcome : Screen(screenName = AppScreens.WelcomeScreen.name)

    data object EditProfile : Screen(screenName = AppScreens.EditProfileScreen.name)

    data object WalletConnect : Screen(screenName = AppScreens.ConnectWalletScannerScreen.name)

    data object ForceAppUpdate : Screen(screenName = AppScreens.ForceUpdateScreen.name)

    data object TermsOfService : Screen(screenName = AppScreens.TermsOfServiceScreen.name), WebBrowserScreen {
        @IgnoredOnParcel
        override val url: String = "https://newm.io/app-tos"
    }

    data object PrivacyPolicy : Screen(screenName = AppScreens.PrivacyPolicyScreen.name), WebBrowserScreen {
        @IgnoredOnParcel
        override val url: String = "https://newm.io/app-privacy"
    }
}

/**
 * A screen that launches a web browser with a specific URL.
 */
interface WebBrowserScreen {
    val url: String
}
