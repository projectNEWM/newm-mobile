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
}
