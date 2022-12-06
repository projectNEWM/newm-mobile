package io.newm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import io.newm.shared.login.UserSession
import org.koin.android.ext.android.inject

class LaunchNewmActivity : ComponentActivity() {

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
        startActivity(Intent(this@LaunchNewmActivity, HomeActivity::class.java))
    }

    private fun launchLoginActivity() {
        startActivity(Intent(this@LaunchNewmActivity, LoginActivity::class.java))
    }

    //TODO: Replace with refresh token from db
    companion object {
        var isUserLoggedIn: Boolean = false
    }
}
