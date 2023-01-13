package io.newm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import io.newm.feature.now.playing.NowPlayingScreen
import io.newm.screens.Screen
import io.newm.screens.home.HomeScreen
import io.newm.screens.marketplace.MarketplaceScreen
import io.newm.screens.library.LibraryScreen
import io.newm.screens.wallet.WalletScreen

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeRoot.route
    ) {
        addHomeTree()
        addTribeTree()
        addStarsTree()
        addWalletTree()
    }
}

private fun NavGraphBuilder.addHomeTree() {
    navigation(
        route = Screen.HomeRoot.route,
        startDestination = Screen.HomeLanding.route
    ) {
        composable(route = Screen.HomeLanding.route) {
            HomeScreen()
        }

        composable(Screen.NowPlayingScreen.route) {
            NowPlayingScreen()
        }
    }
}

private fun NavGraphBuilder.addTribeTree() {
    navigation(
        route = Screen.TribeRoot.route,
        startDestination = Screen.TribeLanding.route
    ) {
        composable(Screen.TribeLanding.route) {
            LibraryScreen()
        }
    }
}

private fun NavGraphBuilder.addStarsTree() {
    navigation(
        route = Screen.StarsRoot.route,
        startDestination = Screen.StarsLanding.route
    ) {
        composable(Screen.StarsLanding.route) {
            MarketplaceScreen()
        }
    }
}

private fun NavGraphBuilder.addWalletTree() {
    navigation(
        route = Screen.WalletRoot.route,
        startDestination = Screen.WalletLanding.route
    ) {
        composable(Screen.WalletLanding.route) {
            WalletScreen()
        }
    }
}