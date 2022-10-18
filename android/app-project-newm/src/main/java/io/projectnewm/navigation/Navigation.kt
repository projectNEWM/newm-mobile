package io.projectnewm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import io.projectnewm.feature.now.playing.NowPlayingScreen
import io.projectnewm.screens.Screen
import io.projectnewm.screens.home.HomeScreen
import io.projectnewm.screens.stars.StarsScreen
import io.projectnewm.screens.tribe.TribeScreen
import io.projectnewm.screens.wallet.WalletScreen

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeRoot.route
    ) {
        addHomeTree(navController)
        addTribeTree(navController)
        addStarsTree()
        addWalletTree(navController)
    }
}

private fun NavGraphBuilder.addHomeTree(
    navController: NavHostController
) {
    navigation(
        route = Screen.HomeRoot.route,
        startDestination = Screen.HomeLanding.route
    ) {
        composable(route = Screen.HomeLanding.route) {
            HomeScreen(onClickSong = { navController.navigate(Screen.NowPlayingScreen.route) })
        }

        composable(Screen.NowPlayingScreen.route) {
            NowPlayingScreen()
        }
    }
}

private fun NavGraphBuilder.addTribeTree(navController: NavHostController) {
    navigation(
        route = Screen.TribeRoot.route,
        startDestination = Screen.TribeLanding.route
    ) {
        composable(Screen.TribeLanding.route) {
            TribeScreen(navController = navController)
        }
    }
}

private fun NavGraphBuilder.addStarsTree() {
    navigation(
        route = Screen.StarsRoot.route,
        startDestination = Screen.StarsLanding.route
    ) {
        composable(Screen.StarsLanding.route) {
            StarsScreen()
        }
    }
}

private fun NavGraphBuilder.addWalletTree(navController: NavHostController) {
    navigation(
        route = Screen.WalletRoot.route,
        startDestination = Screen.WalletLanding.route
    ) {
        composable(Screen.WalletLanding.route) {
            WalletScreen(navController = navController)
        }
    }
}