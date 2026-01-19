package newm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import io.newm.core.theme.NewmTheme
import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.sharedfeatures.devmenu.DebugOverlay
import io.newm.sharedfeatures.screens.DevMenuMainScreen
import io.newm.sharedfeatures.screens.WelcomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    circuit: Circuit,
    config: NewmSharedBuildConfig,
    onRootPop: () -> Unit,
    modifier: Modifier = Modifier
) {
    NewmTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val backstack = rememberSaveableBackStack(WelcomeScreen)

            val circuitNavigator = rememberCircuitNavigator(
                backstack,
                onRootPop = { onRootPop() }
            )

            DebugOverlay(
                buildConfig = config,
                onOpenDebugMenu = { circuitNavigator.goTo(DevMenuMainScreen) }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    NavigableCircuitContent(
                        modifier = modifier,
                        circuit = circuit,
                        navigator = circuitNavigator,
                        backStack = backstack
                    )
                }
            }
        }
    }
}