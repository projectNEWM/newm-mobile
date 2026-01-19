package io.newm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.PopResult
import com.slack.circuit.runtime.screen.Screen
import io.newm.screens.WebBrowserScreen
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.sharedfeatures.screens.HomeScreen
import kotlinx.collections.immutable.ImmutableList

@Composable
fun rememberNewmNavigator(
    circuitNavigator: Navigator,
    logger: NewmAppLogger,
    startHomeActivity: () -> Unit = {},
    launchBrowser: (String) -> Unit,
    eventLogger: NewmAppEventLogger,
): Navigator =
    remember {
        NewmNavigator(circuitNavigator, logger, startHomeActivity, launchBrowser, eventLogger)
    }

private class NewmNavigator(
    private val circuitNavigator: Navigator,
    private val logger: NewmAppLogger,
    private val startHomeActivity: () -> Unit,
    private val launchBrowser: (String) -> Unit,
    private val eventLogger: NewmAppEventLogger,
) : Navigator by circuitNavigator {
    override fun goTo(screen: Screen): Boolean {
        logger.debug(
            tag = "NewmNavigator",
            message = "Navigating to $screen with $circuitNavigator",
        )
        logPageViewEvent(screen)
        return when (screen) {
            is HomeScreen -> {
                startHomeActivity()
                true
            }

            is WebBrowserScreen -> {
                launchBrowser(screen.url)
                true
            }

            else -> {
                circuitNavigator.goTo(screen)
            }
        }
    }

    override fun pop(result: PopResult?): Screen? {
        val screen = circuitNavigator.pop()
        logger.debug(tag = "NewmNavigator", message = "Popping screen: $screen")
        return screen
    }

    override fun resetRoot(
        newRoot: Screen,
        saveState: Boolean,
        restoreState: Boolean,
    ): ImmutableList<Screen> {
        logger.debug(tag = "NewmNavigator", message = "Resetting root to $newRoot")
        logPageViewEvent(newRoot)
        return circuitNavigator.resetRoot(newRoot)
    }

    private fun logPageViewEvent(screen: Screen) {
        if (screen is io.newm.screens.Screen) {
            eventLogger.logPageLoad(screen.screenName)
        } else {
            eventLogger.logPageLoad(screen.javaClass.simpleName)
        }
    }
}
