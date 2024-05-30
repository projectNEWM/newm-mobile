package io.newm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.Screen
import io.newm.feature.login.screen.HomeScreen
import io.newm.screens.WebBrowserScreen


@Composable
fun rememberNewmNavigator(
    circuitNavigator: Navigator,
    startHomeActivity: () -> Unit,
    launchBrowser: (String) -> Unit,
): Navigator = remember {
    NewmNavigator(circuitNavigator, startHomeActivity, launchBrowser)
}

private class NewmNavigator(
    private val circuitNavigator: Navigator,
    private val startHomeActivity: () -> Unit,
    private val launchBrowser: (String) -> Unit,
) : Navigator {
    override fun goTo(screen: Screen) {
        when (screen) {
            is HomeScreen -> startHomeActivity()
            is WebBrowserScreen -> launchBrowser(screen.url)
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
