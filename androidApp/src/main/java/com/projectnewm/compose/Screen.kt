package com.projectnewm.compose

sealed class Screen(val route: String) {
    object HomeRoot : Screen("home-root")
    object TribeRoot : Screen("tribe-root")
    object StarsRoot : Screen("stars-root")
    object WalletRoot : Screen("wallet-root")
}