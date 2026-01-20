package io.newm.sharedfeatures.welcome

import androidx.compose.runtime.Composable

expect class SocialLoginManagerImpl : SocialLoginManager {
    @Composable
    override fun rememberGoogleSignInLauncher(onResult: (GoogleSignInResult) -> Unit): () -> Unit
}
