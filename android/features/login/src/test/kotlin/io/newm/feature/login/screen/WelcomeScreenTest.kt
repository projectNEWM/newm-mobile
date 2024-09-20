package io.newm.feature.login.screen

import androidx.compose.ui.Modifier
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import io.newm.core.test.utils.SnapshotTest
import io.newm.core.test.utils.SnapshotTestConfiguration
import io.newm.feature.login.screen.welcome.WelcomeScreenUi
import io.newm.feature.login.screen.welcome.WelcomeScreenUiState
import io.newm.shared.public.analytics.NewmAppEventLogger
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class WelcomeScreenTest(
    @TestParameter private val testConfiguration: SnapshotTestConfiguration,
) : SnapshotTest(testConfiguration) {

    @Test
    fun default() {
        snapshot {
            WelcomeScreenUi(
                modifier = Modifier,
                state = WelcomeScreenUiState { },
                eventLogger = NewmAppEventLogger()
            )
        }
    }
}
