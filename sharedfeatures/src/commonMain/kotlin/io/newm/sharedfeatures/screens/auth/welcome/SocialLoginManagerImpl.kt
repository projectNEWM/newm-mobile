package io.newm.sharedfeatures.screens.auth.welcome

import androidx.compose.runtime.Composable

expect class SocialLoginManagerImpl : SocialLoginManager {
    @Composable
    override fun rememberGoogleSignInLauncher(onResult: (GoogleSignInResult) -> Unit): () -> Unit
}
