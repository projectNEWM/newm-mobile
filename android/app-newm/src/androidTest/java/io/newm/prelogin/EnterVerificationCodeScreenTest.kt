package io.newm.prelogin

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.karumi.shot.ScreenshotTest
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.newm.LoginActivity
import io.newm.core.theme.NewmTheme
import io.newm.feature.login.screen.*
import io.newm.shared.login.usecases.SignupUseCase
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@Ignore
class EnterVerificationCodeScreenTest : ScreenshotTest {

    init {
        MockKAnnotations.init(this)
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<LoginActivity>()

    @MockK
    private lateinit var useCase: SignupUseCase

    private val vm = CreateAccountViewModel(useCase)

    @Before
    fun setup() {
        composeTestRule.activity.setContent {
            NewmTheme(darkTheme = true) {
                EnterVerificationCodeScreen(vm, {})
            }
        }
    }

    @Test
    fun captureLoginScreen() {
        compareScreenshot(composeTestRule, "Enter Verification Code Screen")
    }
}