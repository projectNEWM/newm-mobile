package io.newm.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.slack.circuit.foundation.CircuitContent
import com.slack.circuit.runtime.Navigator
import io.newm.feature.barcode.scanner.BarcodeScannerActivity
import io.newm.feature.musicplayer.MusicPlayerActivity
import io.newm.screens.Screen
import io.newm.screens.account.WalletConnect
import io.newm.screens.home.HomeScreen
import io.newm.screens.library.LibraryScreen
import io.newm.screens.library.NFTLibraryScreen
import io.newm.screens.profile.edit.ProfileRoute

@Composable
fun Navigation(
    navController: NavHostController, isBottomBarVisible: MutableState<Boolean>
) {
    val onEditProfileClick = { navController.navigate(Screen.EditProfile.route) }
    val onWalletConnect = { navController.navigate(Screen.WalletConnect.route) }
    NavHost(
        navController = navController, startDestination = Screen.NFTLibraryRoot.route
    ) {
        addNFTLibraryTree()
        addHomeTree(navController, isBottomBarVisible)
        addUserAccountTree(onEditProfileClick, onWalletConnect)
        addLibraryTree(navController)
        addMusicPlayerTree()
        addBarcodeScannerTree()
    }
}

private fun NavGraphBuilder.addHomeTree(
    navController: NavHostController, isBottomBarVisible: MutableState<Boolean>,
) {
    navigation(
        route = Screen.HomeRoot.route, startDestination = Screen.HomeLanding.route
    ) {
        composable(route = Screen.HomeLanding.route) {
            HomeScreen(
                onThisWeekViewAll = { }, //TODO: Implement View All screen
                onRecentlyPlayedViewAll = { }, //TODO: Implement View All screen
                onArtistListViewMore = { }, //TODO: Implement View More screen
                onArtistViewDetails = { }, //TODO: Implement Artist Details screen
                onMusicViewDetails = { }, //TODO: Implement Music Details screen
            )
        }
        composable(Screen.EditProfile.route) {
            ProfileRoute(
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(Screen.WalletConnect.route) {
            WalletConnect()
        }

    }
}

private fun NavGraphBuilder.addLibraryTree(navController: NavHostController) {
    navigation(
        route = Screen.LibraryRoot.route,
        startDestination = Screen.LibraryLanding.route
    ) {
        composable(Screen.LibraryLanding.route) {
            LibraryScreen(
                onPlayerClicked = {
                    navController.navigate(Screen.MusicPlayer.route)
                },
                onDownloadSong = {/*TODO*/ },
                onConnectWallet = {/*TODO*/ }
            )
        }
    }
}

private fun NavGraphBuilder.addUserAccountTree(
    onEditProfileClick: () -> Unit,
    onWalletConnect: () -> Unit,
) {
    navigation(
        route = Screen.UserAccountViewRoot.route,
        startDestination = Screen.UserAccount.route
    ) {
        composable(Screen.UserAccount.route) {
            CircuitContent(
                screen = Screen.UserAccount,
                navigator = object : Navigator {
                    override fun goTo(screen: com.slack.circuit.runtime.screen.Screen) {
                        when(screen) {
                            is Screen.EditProfile -> onEditProfileClick()
                            is Screen.WalletConnect -> onWalletConnect()
                        }
                    }

                    override fun pop(): com.slack.circuit.runtime.screen.Screen? {
                        TODO("Not yet implemented")
                    }

                    override fun resetRoot(newRoot: com.slack.circuit.runtime.screen.Screen): List<com.slack.circuit.runtime.screen.Screen> {
                        TODO("Not yet implemented")
                    }
                }
            )
        }
    }
}

private fun NavGraphBuilder.addNFTLibraryTree(
) {
    navigation(
        route = Screen.NFTLibraryRoot.route, startDestination = Screen.NFTLibraryLanding.route
    ) {
        composable(Screen.NFTLibraryLanding.route) {
            NFTLibraryScreen()
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
