package io.newm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.Screen
import io.newm.feature.login.screen.HomeScreen


@Composable
fun rememberNewmNavigator(
    circuitNavigator: Navigator,
    startHomeActivity: () -> Unit,
): Navigator = remember {
    NewmNavigator(circuitNavigator, startHomeActivity)
}

private class NewmNavigator(
    private val circuitNavigator: Navigator,
    private val startHomeActivity: () -> Unit,
) : Navigator {
    override fun goTo(screen: Screen) {
        when (screen) {
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
