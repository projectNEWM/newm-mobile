package io.newm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.Screen
import io.newm.feature.login.screen.HomeScreen
import io.newm.screens.WebBrowserScreen
import io.newm.shared.NewmAppLogger
import io.newm.shared.public.analytics.NewmAppEventLogger


@Composable
fun rememberNewmNavigator(
    circuitNavigator: Navigator,
    logger: NewmAppLogger,
    startHomeActivity: () -> Unit,
    launchBrowser: (String) -> Unit,
    eventLogger: NewmAppEventLogger
): Navigator = remember {
    NewmNavigator(circuitNavigator, logger, startHomeActivity, launchBrowser, eventLogger)
}

private class NewmNavigator(
    private val circuitNavigator: Navigator,
    private val logger: NewmAppLogger,
    private val startHomeActivity: () -> Unit,
    private val launchBrowser: (String) -> Unit,
    private val eventLogger: NewmAppEventLogger
) : Navigator {
    override fun goTo(screen: Screen) {
        logger.debug(tag = "NewmNavigator", message = "Navigating to $screen with $circuitNavigator")
        logPageViewEvent(screen)
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
        logPageViewEvent(newRoot)
        return circuitNavigator.resetRoot(newRoot)
    }

    private fun logPageViewEvent(screen: Screen) {
        if(screen is io.newm.screens.Screen) {
            eventLogger.logPageLoad(screen.screenName)
        } else {
            eventLogger.logPageLoad(screen.javaClass.simpleName)
        }
    }
}
