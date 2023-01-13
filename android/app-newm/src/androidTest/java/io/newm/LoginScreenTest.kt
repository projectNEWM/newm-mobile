package io.newm

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.karumi.shot.ScreenshotTest
import io.newm.core.theme.NewmTheme
import io.newm.feature.login.screen.LoginScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenTest : ScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<LoginActivity>()

    @Before
    fun setup() {
        composeTestRule.activity.setContent {
            NewmTheme(darkTheme = true) {
                LoginScreen(onUserLoggedIn = {})
            }
        }
    }

    @Test
    fun captureLoginScreen() {
        compareScreenshot(composeTestRule)
    }
}