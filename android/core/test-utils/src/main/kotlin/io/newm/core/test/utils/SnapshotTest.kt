package io.newm.core.test.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import app.cash.paparazzi.Paparazzi
import io.newm.sharedfeatures.theme.NewmTheme
import org.jetbrains.compose.resources.PreviewContextConfigurationEffect
import org.junit.Rule

abstract class SnapshotTest(
    private val snapshotTestConfiguration: SnapshotTestConfiguration,
) {
    @get:Rule
    val paparazzi =
        Paparazzi(
            deviceConfig =
                snapshotTestConfiguration.deviceConfig.copy(
                    fontScale = snapshotTestConfiguration.fontScale,
                ),
        )

    fun snapshot(content: @Composable () -> Unit) {
        paparazzi.snapshot {
            CompositionLocalProvider(LocalInspectionMode provides true) {
                PreviewContextConfigurationEffect()
            }
            NewmTheme(darkTheme = snapshotTestConfiguration.isDarkMode, content = content)
        }
    }
}
