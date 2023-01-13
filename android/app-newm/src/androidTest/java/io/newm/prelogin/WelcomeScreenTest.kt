package io.newm.prelogin

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.karumi.shot.ScreenshotTest
import io.newm.LoginActivity
import io.newm.core.theme.NewmTheme
import io.newm.feature.login.screen.WelcomeScreen
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@Ignore
class WelcomeScreenTest : ScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<LoginActivity>()

    @Before
    fun setup() {
        composeTestRule.activity.setContent {
            NewmTheme(darkTheme = true) {
                WelcomeScreen({}, {}, {})
            }
        }
    }

    @Test
    fun captureWelcomeScreen() {
        compareScreenshot(composeTestRule, "WelcomeScreen")
    }
}