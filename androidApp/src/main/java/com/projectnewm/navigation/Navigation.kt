package com.projectnewm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.projectnewm.screens.DetailsScreen
import com.projectnewm.screens.wallet.WalletScreen
import com.projectnewm.screens.Screen
import com.projectnewm.screens.home.HomeScreen
import com.projectnewm.screens.stars.StarsScreen
import com.projectnewm.screens.tribe.TribeScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeRoot.route
    ) {
        addHomeTree(navController)
        addTribeTree(navController)
        addStarsTree(navController)
        addWalletTree(navController)
    }
}

private fun NavGraphBuilder.addHomeTree(navController: NavHostController) {
    navigation(
        route = Screen.HomeRoot.route,
        startDestination = Screen.HomeLanding.route
    ) {
        composable(Screen.HomeLanding.route) {
            HomeScreen(onShowDetails = {param: Int -> navController.navigate(Screen.HomeLanding.Details.route)})
        }

        composable(Screen.HomeLanding.Details.route) {
            DetailsScreen()
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

private fun NavGraphBuilder.addStarsTree(navController: NavHostController) {
    navigation(
        route = Screen.StarsRoot.route,
        startDestination = Screen.StarsLanding.route
    ) {
        composable(Screen.StarsLanding.route) {
            StarsScreen(navController = navController)
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