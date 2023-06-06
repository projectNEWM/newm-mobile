package io.newm.feature.login.screen

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.DeviceConfig.Companion.NEXUS_4
import app.cash.paparazzi.DeviceConfig.Companion.PIXEL_6
import app.cash.paparazzi.Paparazzi
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import io.newm.core.theme.NewmTheme
import io.newm.feature.login.screen.createaccount.CreateAccountScreen
import io.newm.feature.login.screen.createaccount.CreateAccountViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class CreateAccountScreenTest(
    @TestParameter private val theme: TestTheme,
    @TestParameter testDevice: TestDevice,
    @TestParameter fontScale: FontScale,
) {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = testDevice.deviceConfig.copy(fontScale = fontScale.scale),
    )

    @Test
    fun default() {
        paparazzi.snapshot {
            NewmTheme(
                darkTheme = theme == TestTheme.Dark
            ) {
                CreateAccountScreen(
                    userState = CreateAccountViewModel.SignupUserState(),
                    onNext = {},
                    onUserLoggedIn = {},
                    setUserPasswordConfirmation = {},
                    setUserPassword = {},
                    requestCode = {},
                    setUserEmail = {}
                )
            }
        }
    }
}

// TODO move to a different file to use in other test
enum class TestTheme {
    Dark,
    Light,
}

enum class TestDevice(
    val deviceConfig: DeviceConfig,
) {
    Nexus4(NEXUS_4),
    Pixel6(PIXEL_6)
}

enum class FontScale(
    val scale: Float
) {
    NORMAL(scale = 1f),
    LARGE(scale = 2f)
}
