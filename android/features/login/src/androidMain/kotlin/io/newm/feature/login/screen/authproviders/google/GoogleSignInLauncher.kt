package io.newm.feature.login.screen.authproviders.google

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult

interface GoogleSignInLauncher {
    fun launch(launcher: ManagedActivityResultLauncher<Intent, ActivityResult>)
}
