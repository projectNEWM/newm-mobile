package io.newm

//import com.airbnb.android.showkase.models.Showkase
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.newm.core.resources.R
import io.newm.core.theme.*
import io.newm.core.ui.utils.iconGradient
import io.newm.navigation.Navigation
import io.newm.screens.Screen

internal const val TAG_BOTTOM_NAVIGATION = "TAG_BOTTOM_NAVIGATION"

private val HomeIconGradient = iconGradient(LightSkyBlue, DarkViolet)
private val LibraryIconGradient = iconGradient(DarkViolet, DarkPink)
private val SearchIconGradient = iconGradient(DarkPink, BrightOrange)
private val WalletIconGradient = iconGradient(OceanGreen, LightSkyBlue)
private val MarketIconGradient = iconGradient(BrightOrange, YellowJacket)

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
            Navigation(navController, isBottomNavBarVisible)
        }

//        if (BuildConfig.DEBUG) {
//            DebugMenuButton()
//        }
    }
}

@Composable
private fun DebugMenuButton() {
//    val context = LocalContext.current
//    Box(modifier = Modifier.fillMaxSize()) {
//        IconButton(
//            modifier = Modifier.align(Alignment.TopStart),
//            onClick = {
//                context.startActivity(Showkase.getBrowserIntent(context))
//            }) {
//            Icon(
//                imageVector = Icons.Default.Build,
//                contentDescription = "Debug",
//                tint = MaterialTheme.colors.onPrimary
//            )
//        }
//    }
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
                    .testTag(TAG_BOTTOM_NAVIGATION),
                    backgroundColor = Black,
                    contentColor = Gray100
            ) {
                HomeBottomNavigationItem(
                    selected = currentRootScreen == Screen.NFTLibraryRoot,
                    iconResId = R.drawable.ic_library,
                    labelResId = R.string.nft_library,
                    selectedIconBrush = LibraryIconGradient,
                    selectedLabelColor = DarkPink,
                    onClick = { onNavigationSelected(Screen.NFTLibraryRoot) },
                )
                HomeBottomNavigationItem(
                    selected = currentRootScreen == Screen.ProfileViewRoot,
                    iconResId = R.drawable.ic_profile,
                    labelResId = R.string.profile,
                    selectedIconBrush = LibraryIconGradient,
                    selectedLabelColor = DarkPink,
                    onClick = { onNavigationSelected(Screen.ProfileViewRoot) },
                )
                HomeBottomNavigationItem(
                    selected = currentRootScreen == Screen.HomeRoot,
                    iconResId = R.drawable.ic_home,
                    labelResId = R.string.home,
                    selectedIconBrush = HomeIconGradient,
                    selectedLabelColor = DarkViolet,
                    onClick = { onNavigationSelected(Screen.HomeRoot) },
                )
                HomeBottomNavigationItem(
                    selected = currentRootScreen == Screen.LibraryRoot,
                    iconResId = R.drawable.ic_library,
                    labelResId = R.string.library,
                    selectedIconBrush = LibraryIconGradient,
                    selectedLabelColor = DarkPink,
                    onClick = { onNavigationSelected(Screen.LibraryRoot) },
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

// Based on content from: https://github.com/wlara/android-next-gen/blob/main/app/src/main/java/com/github/wlara/nextgen/ui/home/HomeScreen.kt
@Composable
private fun RowScope.HomeBottomNavigationItem(
    selected: Boolean,
    @DrawableRes iconResId: Int,
    @StringRes labelResId: Int,
    selectedIconBrush: Brush,
    selectedLabelColor: Color,
    onClick: () -> Unit,
) {
    val label = stringResource(id = labelResId)
    BottomNavigationItem(
        icon = {
            Icon(
                modifier = if (selected) {
                    Modifier
                        .align(Alignment.CenterVertically)
                        .graphicsLayer(alpha = 0.99f)   // After migration to compose 1.4+ replace with .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                        .drawWithCache {
                            onDrawWithContent {
                                drawContent()
                                drawRect(selectedIconBrush, blendMode = BlendMode.SrcAtop)
                            }
                        }
                } else {
                    Modifier.align(Alignment.CenterVertically)
                },
                painter = painterResource(id = iconResId),
                contentDescription = label,
            )
        },
        label = {
            Text(
                text = label,
                fontFamily = inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 10.sp,
                color = if (selected) selectedLabelColor else Color.Unspecified
            )
        },
        selected = selected,
        onClick = onClick
    )
}

@Composable
private fun NavController.currentRootScreenAsState(): State<Screen> {
    val currentRootScreen = remember { mutableStateOf<Screen>(Screen.NFTLibraryRoot) }
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
        Screen.Profile.route
    )
}
