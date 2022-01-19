package com.projectnewm

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.projectnewm.components.NewmRainbowDivider
import com.projectnewm.navigation.Navigation
import com.projectnewm.screens.Screen
import kotlinx.coroutines.flow.collect

@Composable
internal fun NewmApp(
    navController: NavHostController = rememberNavController()
) {
    val currentRootScreen by navController.currentRootScreenAsState()
    Scaffold(
        bottomBar = {
            NewmBottomNavigation(
                currentRootScreen = currentRootScreen,
                onNavigationSelected = { navController.navigate(it.route) }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Navigation(navController)
        }
    }
}

@Composable
internal fun NewmBottomNavigation(
    currentRootScreen: Screen,
    onNavigationSelected: (Screen) -> Unit
) {
    Column(Modifier.height(76.dp)) {
        NewmRainbowDivider()
        BottomNavigation(
            backgroundColor = Color.Black,
            contentColor = Color.White,
            modifier = Modifier.fillMaxHeight()
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

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    NewmBottomNavigation(Screen.HomeRoot) {}
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
