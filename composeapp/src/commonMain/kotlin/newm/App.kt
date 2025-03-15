package newm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import newm.ui.screens.welcome.WelcomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    circuit: Circuit,
    onRootPop: () -> Unit,
    modifier: Modifier = Modifier
) {
    MaterialTheme {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val backstack = rememberSaveableBackStack(WelcomeScreen)

            val circuitNavigator = rememberCircuitNavigator(
                backstack,
                onRootPop = { onRootPop() }
            )

            NavigableCircuitContent(
                modifier = modifier,
                circuit = circuit,
                navigator = circuitNavigator,
                backStack = backstack
            )
        }
    }
}