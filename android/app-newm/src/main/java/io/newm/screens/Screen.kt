package io.newm.screens

import kotlinx.parcelize.Parcelize
import com.slack.circuit.runtime.Screen as CircuitScreen

sealed class Screen(val route: String) {
    //High Navigation Roots
    object HomeRoot : Screen("home-root")
    object HomeLanding : Screen("home-landing")

    object ProfileViewRoot : Screen("profile-view-root")

    object ProfileViewLanding : Screen("profile-view-landing")

    object LibraryRoot : Screen("library-root")

    object LibraryLanding : Screen("library-landing")

    object NFTLibraryRoot : Screen("nft-library-root")

    object NFTLibraryLanding : Screen("nft-library-landing")

    //Single Screens
    @Parcelize
    object LoginLandingScreen : Screen("login-landing"), CircuitScreen

    @Parcelize
    object LoginScreen : Screen("login"), CircuitScreen
    object Signup : Screen("signup")
    object Profile : Screen("profile")
    object BarcodeScanner : Screen("barcode-scanner")

    object MusicPlayer : Screen("music-player/{songId}") {
        fun routeOf(songId: String) = "music-player/$songId"
    }
}
