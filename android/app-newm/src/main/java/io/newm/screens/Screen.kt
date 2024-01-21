package io.newm.screens

import kotlinx.parcelize.Parcelize
import com.slack.circuit.runtime.screen.Screen as CircuitScreen

sealed class Screen(val route: String) {
    //High Navigation Roots
    data object HomeRoot : Screen("home-root")
    data object HomeLanding : Screen("home-landing")

    data object UserAccountViewRoot : Screen("user-account-view-root")

    data object UserAccountViewLanding : Screen("user-account-view-landing")

    data object LibraryRoot : Screen("library-root")

    data object LibraryLanding : Screen("library-landing")

    data object NFTLibraryRoot : Screen("nft-library-root")

    data object NFTLibraryLanding : Screen("nft-library-landing")

    //Single Screens
    @Parcelize
    object LoginLandingScreen : Screen("login-landing"), CircuitScreen

    @Parcelize
    object LoginScreen : Screen("login"), CircuitScreen
    data object Signup : Screen("signup")
    data object EditProfile : Screen("edit-profile")
    data object BarcodeScanner : Screen("barcode-scanner")

    data object MusicPlayer : Screen("music-player/{songId}") {
        fun routeOf(songId: String) = "music-player/$songId"
    }
}
