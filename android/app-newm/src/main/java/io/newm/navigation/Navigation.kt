package io.newm.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import io.newm.feature.barcode.scanner.BarcodeScannerActivity
import io.newm.feature.musicplayer.MusicPlayerActivity
import io.newm.feature.now.playing.DemoPlayerActivity
import io.newm.screens.Screen
import io.newm.screens.home.HomeScreen
import io.newm.screens.library.LibraryScreen
import io.newm.screens.library.NFTLibraryScreen
import io.newm.screens.profile.ProfileRoute
import io.newm.screens.search.SearchScreen
import io.newm.shared.models.Song

@Composable
fun Navigation(
    navController: NavHostController, isBottomBarVisible: MutableState<Boolean>
) {
    val context = LocalContext.current
    NavHost(
        navController = navController, startDestination = Screen.NFTLibraryRoot.route
    ) {
        addNFTLibraryTree(
            onPlaySong = { song ->
                context.startActivity(DemoPlayerActivity.createIntent(context, song.id))
            },
            onConnectWalletClick = { navController.navigate(Screen.BarcodeScanner.route) }
        )
        addHomeTree(navController, isBottomBarVisible)
        addSearchTree()
        addLibraryTree(navController)
        addMusicPlayerTree()
        addBarcodeScannerTree()
    }
}

private fun NavGraphBuilder.addHomeTree(
    navController: NavHostController, isBottomBarVisible: MutableState<Boolean>
) {
    val nowPlaying = { navController.navigate(Screen.NowPlayingScreen.route) }
    navigation(
        route = Screen.HomeRoot.route, startDestination = Screen.HomeLanding.route
    ) {
        composable(route = Screen.HomeLanding.route) {
            HomeScreen(
                onShowProfile = { navController.navigate(Screen.Profile.route) },
                onThisWeekViewAll = { nowPlaying.invoke() }, //TODO: Implement View All screen
                onRecentlyPlayedViewAll = { nowPlaying.invoke() }, //TODO: Implement View All screen
                onArtistListViewMore = { nowPlaying.invoke() }, //TODO: Implement View More screen
                onArtistViewDetails = { nowPlaying.invoke() }, //TODO: Implement Artist Details screen
                onMusicViewDetails = { nowPlaying.invoke() }, //TODO: Implement Music Details screen
            )
        }

//        composable(Screen.NowPlayingScreen.route) {
//            MusicPlayerScreen()
//        }
        composable(Screen.Profile.route) {
            ProfileRoute(
                isBottomBarVisible = isBottomBarVisible,
                onNavigateUp = { navController.navigateUp() },
            )
        }
    }
}

private fun NavGraphBuilder.addLibraryTree(navController: NavHostController) {
    navigation(
        route = Screen.LibraryRoot.route, startDestination = Screen.LibraryLanding.route
    ) {
        composable(Screen.LibraryLanding.route) {
            LibraryScreen(
                onSongPlay = { songId ->
                    navController.navigate(Screen.MusicPlayer.routeOf(songId))
                },
                onArtistViewDetails = {},//TODO: Implement on artist view
                onAlbumViewDetails = {}//TODO: Implement on album view
            )
        }
    }
}

private fun NavGraphBuilder.addNFTLibraryTree(
    onPlaySong: (Song) -> Unit, onConnectWalletClick: () -> Unit
) {
    navigation(
        route = Screen.NFTLibraryRoot.route, startDestination = Screen.NFTLibraryLanding.route
    ) {
        composable(Screen.NFTLibraryLanding.route) { backStackEntry ->
            NFTLibraryScreen(onPlaySong, onConnectWalletClick)
        }
    }
}

private fun NavGraphBuilder.addSearchTree() {
    navigation(
        route = Screen.SearchRoot.route, startDestination = Screen.SearchLanding.route
    ) {
        composable(Screen.SearchLanding.route) {
            SearchScreen()
        }
    }
}

private fun NavGraphBuilder.addMusicPlayerTree() {
    activity(
        route = Screen.MusicPlayer.route
    ) {
        activityClass = MusicPlayerActivity::class
        argument("songId") { type = NavType.StringType }
    }
}

private fun NavGraphBuilder.addBarcodeScannerTree() {
    activity(
        route = Screen.BarcodeScanner.route
    ) {
        activityClass = BarcodeScannerActivity::class
    }
}
