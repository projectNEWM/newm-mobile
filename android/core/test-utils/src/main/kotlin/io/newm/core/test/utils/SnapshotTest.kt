package io.newm.core.test.utils

import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import io.newm.core.theme.NewmTheme
import org.junit.Rule

abstract class SnapshotTest(
    private val snapshotTestConfiguration: SnapshotTestConfiguration,
) {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = snapshotTestConfiguration.deviceConfig.copy(fontScale = snapshotTestConfiguration.fontScale),
    )

    fun snapshot(
        content: @Composable () -> Unit,
    ) {
        paparazzi.snapshot {
            NewmTheme(
                darkTheme = snapshotTestConfiguration.isDarkMode,
                content = content,
            )
        }
    }
}
