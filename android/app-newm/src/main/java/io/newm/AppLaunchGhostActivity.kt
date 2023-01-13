package io.newm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import io.newm.shared.login.UserSession
import org.koin.android.ext.android.inject

class AppLaunchGhostActivity : ComponentActivity() {

    private val userSession: UserSession by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { true }
        }

        if (userSession.isUserLoggedIn()) {
            launchHomeActivity()
        } else {
            launchLoginActivity()
        }
        finish()
    }

    private fun launchHomeActivity() {
        startActivity(Intent(this@AppLaunchGhostActivity, HomeActivity::class.java))
    }

    private fun launchLoginActivity() {
        startActivity(Intent(this@AppLaunchGhostActivity, LoginActivity::class.java))
    }
}
