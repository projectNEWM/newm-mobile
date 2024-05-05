package io.newm.screens

import kotlinx.parcelize.Parcelize
import com.slack.circuit.runtime.screen.Screen as CircuitScreen

@Parcelize
sealed class Screen(val route: String) : CircuitScreen {
    //High Navigation Roots
    data object HomeRoot : Screen("home-root")
    data object HomeLanding : Screen("home-landing")

    @Parcelize
    data object UserAccountViewRoot : Screen("user-account-view-root")

    data object UserAccount : Screen("user-account-view-landing")

    data object LibraryRoot : Screen("library-root")

    data object LibraryLanding : Screen("library-landing")

    data object NFTLibraryRoot : Screen("nft-library-root")

    data object NFTLibraryLanding : Screen("nft-library-landing")

    //Single Screens
    @Parcelize
    object LoginLandingScreen : Screen("login-landing")
    
    data object Signup : Screen("signup")
    data object EditProfile : Screen("edit-profile")

    data object WalletConnect : Screen("wallet-connect")

    data object BarcodeScanner : Screen("barcode-scanner")

    data object MusicPlayer : Screen("music-player/{songId}")
}
