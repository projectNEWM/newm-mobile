package io.newm.screens

import kotlinx.parcelize.Parcelize
import com.slack.circuit.runtime.screen.Screen as CircuitScreen

@Parcelize
sealed class Screen : CircuitScreen {
    data object UserAccount : Screen()

    data object NFTLibrary : Screen()

    data object LoginLandingScreen : Screen()

    data object EditProfile : Screen()

    data object WalletConnect : Screen()

    data object ForceAppUpdate : Screen()

    data object TermsAndConditions : Screen(), WebBrowserScreen {
        override val url: String = "https://newm.io/terms-and-conditions"
    }

    data object PrivacyPolicy : Screen(), WebBrowserScreen {
        override val url: String = "https://newm.io/privacy-policy"

    }
}

/**
 * A screen that launches a web browser with a specific URL.
 */
interface WebBrowserScreen {
    val url: String
}
