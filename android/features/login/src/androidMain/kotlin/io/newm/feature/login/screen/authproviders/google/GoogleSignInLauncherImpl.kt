package io.newm.feature.login.screen.authproviders.google

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class GoogleSignInLauncherImpl(
    private val googleSignInClient: GoogleSignInClient,
) : GoogleSignInLauncher {
    override fun launch(launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
        launcher.launch(googleSignInClient.signInIntent)
    }
}
