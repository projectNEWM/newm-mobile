package io.newm.screens

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import com.slack.circuit.runtime.screen.Screen as CircuitScreen

@Parcelize
sealed class Screen(val screenName: String) : CircuitScreen {

    data object UserAccount : Screen(screenName = "Account View")

    data object NFTLibrary : Screen(screenName = "NFT Library")

    data object LoginLandingScreen : Screen(screenName = "Login Landing Screen")

    data object EditProfile : Screen(screenName = "Edit Profile")

    data object WalletConnect : Screen(screenName = "Wallet Connect")

    data object ForceAppUpdate : Screen(screenName = "Force App Update")

    data object TermsAndConditions : Screen(screenName = "Terms And Conditions"), WebBrowserScreen {
        @IgnoredOnParcel
        override val url: String = "https://newm.io/terms-and-conditions"
    }

    data object PrivacyPolicy : Screen(screenName = "Privacy Policy"), WebBrowserScreen {
        @IgnoredOnParcel
        override val url: String = "https://newm.io/privacy-policy"

    }
}

/**
 * A screen that launches a web browser with a specific URL.
 */
interface WebBrowserScreen {
    val url: String
}
