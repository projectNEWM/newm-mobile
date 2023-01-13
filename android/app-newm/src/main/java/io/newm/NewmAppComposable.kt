package io.newm

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.airbnb.android.showkase.models.Showkase
import io.newm.navigation.Navigation
import io.newm.screens.Screen
import io.newm.core.resources.R

internal const val TAG_BOTTOM_NAVIGATION = "TAG_BOTTOM_NAVIGATION"

@Composable
internal fun NewmApp(
    navController: NavHostController = rememberNavController()
) {
    val currentRootScreen by navController.currentRootScreenAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val isBottomNavBarVisible = rememberSaveable { (mutableStateOf(true)) }
    isBottomNavBarVisible.value =
        !routesWithoutBottomNavBar.contains(navBackStackEntry?.destination?.route)

    Scaffold(
        bottomBar = {
            NewmBottomNavigation(
                currentRootScreen = currentRootScreen,
                isVisible = isBottomNavBarVisible.value,
                onNavigationSelected = {
                    navController.navigate(it.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true

                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Navigation(navController)
        }

        if (BuildConfig.DEBUG) {
            DebugMenuButton()
        }
    }
}

@Composable
private fun DebugMenuButton() {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            modifier = Modifier.align(Alignment.TopStart),
            onClick = { context.startActivity(Showkase.getBrowserIntent(context)) }) {
            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = "Debug",
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
internal fun NewmBottomNavigation(
    currentRootScreen: Screen,
    isVisible: Boolean,
    onNavigationSelected: (Screen) -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        Column(Modifier.height(76.dp)) {
            BottomNavigation(
                modifier = Modifier
                    .fillMaxHeight()
                    .testTag(TAG_BOTTOM_NAVIGATION)
            ) {
                HomeBottomNavigationItem(
                    icon = R.drawable.ic_home,
                    selected = currentRootScreen == Screen.HomeRoot,
                    onClick = { onNavigationSelected(Screen.HomeRoot) },
                    contentDescription = stringResource(R.string.home)
                )
                HomeBottomNavigationItem(
                    icon = R.drawable.ic_library,
                    selected = currentRootScreen == Screen.TribeRoot,
                    onClick = { onNavigationSelected(Screen.TribeRoot) },
                    contentDescription = stringResource(R.string.library)
                )
                HomeBottomNavigationItem(
                    icon = R.drawable.ic_wallet,
                    selected = currentRootScreen == Screen.WalletRoot,
                    onClick = { onNavigationSelected(Screen.WalletRoot) },
                    contentDescription = stringResource(R.string.wallet)
                )
                HomeBottomNavigationItem(
                    icon = R.drawable.ic_marketplace,
                    selected = currentRootScreen == Screen.StarsRoot,
                    onClick = { onNavigationSelected(Screen.StarsRoot) },
                    contentDescription = stringResource(R.string.marketplace)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    NewmBottomNavigation(Screen.HomeRoot, true) {}
}

@Composable
private fun RowScope.HomeBottomNavigationItem(
    @DrawableRes icon: Int,
    selected: Boolean,
    onClick: () -> Unit,
    contentDescription: String?
) {
    BottomNavigationItem(
        icon = { Icon(painterResource(id = icon), contentDescription = contentDescription) },
        modifier = Modifier.align(Alignment.CenterVertically),
        selected = selected,
        onClick = onClick
    )
}

@Composable
private fun NavController.currentRootScreenAsState(): State<Screen> {
    val currentRootScreen = remember { mutableStateOf<Screen>(Screen.HomeRoot) }
    LaunchedEffect(this) {
        currentBackStackEntryFlow.collect { entry ->
            allScreens.find { entry.destination.parent?.route == it.route }?.let {
                currentRootScreen.value = it
            }
        }
    }
    return currentRootScreen
}

val allScreens: List<Screen>
    get() = Screen::class.sealedSubclasses.map { it.objectInstance as Screen }

val routesWithoutBottomNavBar: List<String> by lazy {
    listOf(
        Screen.NowPlayingScreen.route
    )
}