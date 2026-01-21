package io.newm.sharedfeatures.paparazzi

import androidx.compose.ui.Modifier
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import io.newm.core.test.utils.SnapshotTest
import io.newm.core.test.utils.SnapshotTestConfiguration
import io.newm.sharedfeatures.devmenu.DevMenuUi
import io.newm.sharedfeatures.screens.DevMenuItem
import io.newm.sharedfeatures.screens.DevMenuMainScreen
import io.newm.sharedfeatures.screens.FeatureFlagsListScreen
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class DevMenuUiTest(
    @TestParameter configuration: SnapshotTestConfiguration,
) : SnapshotTest(configuration) {
    @Test
    fun defaultDevMenuUi() {
        snapshot {
            DevMenuUi(
                state =
                    DevMenuMainScreen.UiState.Content(
                        menuItems =
                            listOf(
                                DevMenuItem(
                                    "Feature Flags",
                                    FeatureFlagsListScreen,
                                    "Manage feature toggles",
                                ),
                                DevMenuItem("Debug Info", FeatureFlagsListScreen, "App diagnostics"),
                            ),
                        onEvent = {},
                    ),
                modifier = Modifier,
            )
        }
    }

    @Test
    fun loadingDevMenuUi() {
        snapshot { DevMenuUi(state = DevMenuMainScreen.UiState.Loading, modifier = Modifier) }
    }
}
