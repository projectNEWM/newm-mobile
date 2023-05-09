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
import io.newm.screens.search.SearchScreen
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
        addSearchTree()
        addLibraryTree()
    }
}

private fun NavGraphBuilder.addHomeTree() {
    navigation(
        route = Screen.HomeRoot.route,
        startDestination = Screen.HomeLanding.route
    ) {
        composable(route = Screen.HomeLanding.route) {
            HomeScreen(
                onShowProfile = {}, //TODO: Implement profile screen
                onThisWeekViewAll = {}, //TODO: Implement View All screen
                onRecentlyPlayedViewAll = {}, //TODO: Implement View All screen
                onArtistListViewMore = {}, //TODO: Implement View More screen
                onArtistViewDetails = {}, //TODO: Implement Artist Details screen
                onMusicViewDetails = {}, //TODO: Implement Music Details screen
            )
        }

        composable(Screen.NowPlayingScreen.route) {
            NowPlayingScreen()
        }
    }
}

private fun NavGraphBuilder.addLibraryTree() {
    navigation(
        route = Screen.LibraryRoot.route,
        startDestination = Screen.LibraryLanding.route
    ) {
        composable(Screen.LibraryLanding.route) {
            LibraryScreen()
        }
    }
}

private fun NavGraphBuilder.addSearchTree() {
    navigation(
        route = Screen.SearchRoot.route,
        startDestination = Screen.SearchLanding.route
    ) {
        composable(Screen.SearchLanding.route) {
            SearchScreen()
        }
    }
}
