package io.projectnewm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class LaunchNewmActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { true }
        }

        if (isUserLoggedIn) {
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
