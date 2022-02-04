package com.projectnewm.screens

sealed class Screen(val route: String) {
    object HomeRoot : Screen("home-root")
    object TribeRoot : Screen("tribe-root")
    object StarsRoot : Screen("stars-root")
    object WalletRoot : Screen("wallet-root")

    object HomeLanding : Screen("home-landing") {
        object Details : Screen("details")
    }

    object TribeLanding : Screen("tribe-landing")
    object StarsLanding : Screen("stars-landing")
    object WalletLanding : Screen("wallet-landing")
}