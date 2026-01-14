package io.newm.sharedfeatures.login

import androidx.compose.runtime.Composable

data class GoogleUser(val idToken: String)

interface GoogleSignInLauncher {
    fun launch()
}

@Composable
expect fun rememberGoogleSignInLauncher(onResult: (Result<GoogleUser>) -> Unit): GoogleSignInLauncher
