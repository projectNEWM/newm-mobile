package io.newm.feature.login.screen

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import io.newm.core.test.utils.SnapshotTest
import io.newm.core.test.utils.SnapshotTestConfiguration
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class WelcomeScreenTest(
    @TestParameter private val testConfiguration: SnapshotTestConfiguration,
) : SnapshotTest(testConfiguration) {

    @Test
    fun default() {
        snapshot {
            WelcomeScreen(
                onLogin = {},
                onCreateAccount = {},
                onContinueAsGuest = {},
            )
        }
    }
}
