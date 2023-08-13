package io.newm.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import io.newm.feature.now.playing.NowPlayingScreen
import io.newm.screens.Screen
import io.newm.screens.home.HomeScreen
import io.newm.screens.library.LibraryScreen
import io.newm.screens.profile.ProfileScreen
import io.newm.screens.search.SearchScreen

@Composable
fun Navigation(
    navController: NavHostController,
    isBottomBarVisible: MutableState<Boolean>
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeRoot.route
    ) {
        addHomeTree(navController, isBottomBarVisible)
        addSearchTree()
        addLibraryTree()
    }
}

private fun NavGraphBuilder.addHomeTree(
    navController: NavHostController,
    isBottomBarVisible: MutableState<Boolean>
) {
    navigation(
        route = Screen.HomeRoot.route,
        startDestination = Screen.HomeLanding.route
    ) {
        composable(route = Screen.HomeLanding.route) {
            HomeScreen(
                onShowProfile = { navController.navigate(Screen.Profile.route) },
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
        composable(Screen.Profile.route) {
            ProfileScreen(
                isBottomBarVisible = isBottomBarVisible,
                onNavigateUp = { navController.navigateUp() },
                onLogout = {}, //TODO: Implement logout functionality
                onShowTermsAndConditions = {}, //TODO: Link the appropriate page
                onShowPrivacyPolicy = {}, //TODO: Link the appropriate page
                onShowDocuments = {}, //TODO: Link the appropriate page
                onShowAskTheCommunity = {}, //TODO: Link the appropriate page
                onShowFaq = {}, //TODO: Link the appropriate page
            )
        }
    }
}

private fun NavGraphBuilder.addLibraryTree() {
    navigation(
        route = Screen.LibraryRoot.route,
        startDestination = Screen.LibraryLanding.route
    ) {
        composable(Screen.LibraryLanding.route) {
            LibraryScreen(
                onSongView = {} //TODO: Implement on song view
            )
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

