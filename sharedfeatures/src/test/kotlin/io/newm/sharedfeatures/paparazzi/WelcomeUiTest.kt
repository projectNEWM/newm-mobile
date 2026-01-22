package io.newm.sharedfeatures.paparazzi

import androidx.compose.ui.Modifier
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import io.newm.core.test.utils.SnapshotTest
import io.newm.core.test.utils.SnapshotTestConfiguration
import io.newm.sharedfeatures.screens.WelcomeScreen.UiState
import io.newm.sharedfeatures.screens.auth.welcome.WelcomeUi
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class WelcomeUiTest(
    @TestParameter configuration: SnapshotTestConfiguration,
) : SnapshotTest(configuration) {
    @Test
    fun defaultWelcomeUi() {
        snapshot { WelcomeUi(state = UiState.Content(onEvent = {}), modifier = Modifier) }
    }
}
