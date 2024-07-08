package io.newm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.Screen
import io.newm.feature.login.screen.HomeScreen
import io.newm.screens.WebBrowserScreen
import io.newm.shared.NewmAppLogger


@Composable
fun rememberNewmNavigator(
    circuitNavigator: Navigator,
    logger: NewmAppLogger,
    startHomeActivity: () -> Unit,
    launchBrowser: (String) -> Unit,
): Navigator = remember {
    NewmNavigator(circuitNavigator, logger, startHomeActivity, launchBrowser)
}

private class NewmNavigator(
    private val circuitNavigator: Navigator,
    private val logger: NewmAppLogger,
    private val startHomeActivity: () -> Unit,
    private val launchBrowser: (String) -> Unit,
) : Navigator {
    override fun goTo(screen: Screen) {
        logger.debug(tag = "NewmNavigator", message = "Navigating to $screen")
        when (screen) {
            is HomeScreen -> startHomeActivity()
            is WebBrowserScreen -> launchBrowser(screen.url)
            else -> circuitNavigator.goTo(screen)
        }
    }

    override fun pop(): Screen? {
        val screen = circuitNavigator.pop()
        logger.debug(tag = "NewmNavigator", message = "Popping screen: $screen")
        return screen
    }

    override fun resetRoot(newRoot: Screen): List<Screen> {
        logger.debug(tag = "NewmNavigator", message = "Resetting root to $newRoot")
        return circuitNavigator.resetRoot(newRoot)
    }
}
