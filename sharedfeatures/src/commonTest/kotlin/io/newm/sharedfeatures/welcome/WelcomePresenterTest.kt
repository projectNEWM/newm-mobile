package io.newm.sharedfeatures.welcome

import com.slack.circuit.test.FakeNavigator
import com.slack.circuit.test.test
import io.newm.shared.AppLogger
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonPublic.analytics.IEventLogger
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.models.User
import io.newm.shared.commonPublic.usecases.LoginUseCase
import io.newm.sharedfeatures.login.GoogleSignInLauncher
import io.newm.sharedfeatures.login.GoogleUser
import io.newm.sharedfeatures.login.RecaptchaManager
import io.newm.sharedfeatures.screens.HomeScreen
import io.newm.sharedfeatures.screens.WelcomeScreen
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WelcomePresenterTest {

    @Test
    fun `Google Sign-In success navigates to HomeScreen`() = runTest {
        val navigator = FakeNavigator(WelcomeScreen)
        val loginUseCase = FakeLoginUseCase()
        val recaptchaManager = FakeRecaptchaManager()
        val googleSignInLauncher = FakeGoogleSignInLauncher()
        
        val logger = NewmAppLogger().apply { 
            setClientLogger(FakeAppLogger()) 
        }
        val analyticsTracker = NewmAppEventLogger().apply { 
            setClientAnalyticsTracker(FakeEventLogger()) 
        }

        val presenter = WelcomePresenter(
            navigator = navigator,
            loginUseCase = { loginUseCase },
            recaptchaManager = recaptchaManager,
            logger = logger,
            analyticsTracker = analyticsTracker,
            googleSignInLauncherFactory = { onResult ->
                googleSignInLauncher.onResult = onResult
                googleSignInLauncher
            }
        )

        presenter.test {
            val state = awaitItem()
            assertTrue(state is WelcomeScreen.UiState.Content)
            
            // Trigger Google Sign In
            (state as WelcomeScreen.UiState.Content).onEvent(WelcomeScreen.UiEvent.OnGoogleSignIn)
            
            // Verify Launcher was called
            assertTrue(googleSignInLauncher.launchCalled)
            
            // Simulate Success
            val idToken = "mock_id_token"
            googleSignInLauncher.emitSuccess(GoogleUser(idToken))
            
            // Verify Recaptcha and Login called
            assertEquals("mock_recaptcha_token", loginUseCase.verificationCode)
            assertEquals(idToken, loginUseCase.idToken)
            
            // Verify Navigation
            assertEquals(HomeScreen, navigator.awaitNextScreen())
        }
    }
}

// Fakes
class FakeLoginUseCase : LoginUseCase {
    var idToken: String? = null
    var verificationCode: String? = null
    
    override suspend fun logInWithGoogle(idToken: String, humanVerificationCode: String) {
        this.idToken = idToken
        this.verificationCode = humanVerificationCode
    }
    
    override suspend fun logIn(email: String, password: String, humanVerificationCode: String) {}
    override suspend fun logInWithFacebook(accessToken: String) {}
    override suspend fun logInWithLinkedIn(accessToken: String) {}
    override suspend fun logInWithApple(idToken: String, humanVerificationCode: String) {}
    override suspend fun logout() {}
    
    // NOTE: These match the signatures found in LoginUseCase.kt? No, LoginUseCase.kt didn't have register/forgot/reset in the file I read.
    // Wait, the file I read for LoginUseCase ONLY had logIn, logInWithGoogle, logInWithFacebook, logInWithLinkedIn, logInWithApple, logout.
    // So I should NOT implement register/forgot/reset here unless they were added in an extension or I missed them.
    // The previous error said: "'register' overrides nothing". So I will remove them.
}

class FakeRecaptchaManager : RecaptchaManager {
    override suspend fun executeLogin(): Result<String> {
        return Result.success("mock_recaptcha_token")
    }
}

class FakeGoogleSignInLauncher : GoogleSignInLauncher {
    var launchCalled = false
    var onResult: ((Result<GoogleUser>) -> Unit)? = null
    
    override fun launch() {
        launchCalled = true
    }
    
    fun emitSuccess(user: GoogleUser) {
        onResult?.invoke(Result.success(user))
    }
}

class FakeAppLogger : AppLogger {
    override fun user(userId: String) {}
    override fun debug(tag: String, message: String) {}
    override fun info(tag: String, message: String) {}
    override fun error(tag: String, message: String, exception: Throwable) {}
    override fun breadcrumb(tag: String, message: String) {}
}

class FakeEventLogger : IEventLogger {
    override fun setUserId(userId: String) {}
    override fun setUserProperty(propertyName: String, value: String) {}
    override fun logEvent(eventName: String, properties: Map<String, Any?>?) {}
    override fun logPageLoad(screenName: String, properties: Map<String, Any?>?) {}
    override fun logClickEvent(buttonName: String, properties: Map<String, Any?>?) {}
}