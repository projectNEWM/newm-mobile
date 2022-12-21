package io.newm

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.ui.graphics.Color
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
import io.newm.components.NewmRainbowDivider
import io.newm.core.theme.NewmBlack
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

@OptIn(ExperimentalAnimationApi::class)
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
            NewmRainbowDivider()
            BottomNavigation(
                backgroundColor = NewmBlack,
                contentColor = Color.White,
                modifier = Modifier
                    .fillMaxHeight()
                    .testTag(TAG_BOTTOM_NAVIGATION)
            ) {
                HomeBottomNavigationItem(
                    icon = R.drawable.ic_home,
                    selected = currentRootScreen == Screen.HomeRoot,
                    label = stringResource(R.string.home),
                    onClick = { onNavigationSelected(Screen.HomeRoot) }
                )
                HomeBottomNavigationItem(
                    icon = R.drawable.ic_tribe,
                    selected = currentRootScreen == Screen.TribeRoot,
                    label = stringResource(R.string.tribe),
                    onClick = { onNavigationSelected(Screen.TribeRoot) }
                )
                HomeBottomNavigationItem(
                    icon = R.drawable.ic_stars,
                    selected = currentRootScreen == Screen.StarsRoot,
                    label = stringResource(R.string.stars),
                    onClick = { onNavigationSelected(Screen.StarsRoot) }
                )
                HomeBottomNavigationItem(
                    icon = R.drawable.ic_wallet,
                    selected = currentRootScreen == Screen.WalletRoot,
                    label = stringResource(R.string.wallet),
                    onClick = { onNavigationSelected(Screen.WalletRoot) }
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
    label: String,
    onClick: () -> Unit,
) {
    BottomNavigationItem(
        icon = { Icon(painterResource(id = icon), contentDescription = label) },
        modifier = Modifier.align(Alignment.CenterVertically),
        label = { Text(label) },
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