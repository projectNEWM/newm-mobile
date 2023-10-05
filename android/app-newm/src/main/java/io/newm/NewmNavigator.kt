package io.newm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.Screen
import io.newm.feature.login.screen.HomeScreen
import io.newm.feature.login.screen.LoginScreen


@Composable
fun rememberNewmNavigator(
    circuitNavigator: Navigator,
    navHostController: NavHostController,
    startHomeActivity: () -> Unit,
): Navigator = remember {
    NewmNavigator(circuitNavigator, navHostController, startHomeActivity)
}

private class NewmNavigator(
    private val circuitNavigator: Navigator,
    private val navController: NavHostController,
    private val startHomeActivity: () -> Unit,
) : Navigator {
    override fun goTo(screen: Screen) {
        when (screen) {
            is io.newm.screens.Screen -> navController.navigate(screen.route)
            is LoginScreen -> navController.navigate(io.newm.screens.Screen.LoginScreen.route)
            is HomeScreen -> startHomeActivity()
            else -> circuitNavigator.goTo(screen)
        }
    }

    override fun pop(): Screen? {
        return circuitNavigator.pop()
    }

    override fun resetRoot(newRoot: Screen): List<Screen> {
        return circuitNavigator.resetRoot(newRoot)
    }
}
