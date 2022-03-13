package io.projectnewm

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.karumi.shot.ScreenshotTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.projectnewm.screens.login.LoginScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class LoginScreenTest : ScreenshotTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            LoginScreen(onSignInSubmitted = { email, password -> })
        }
    }

    @Test
    fun captureLoginScreen() {
        compareScreenshot(composeTestRule)
    }
}