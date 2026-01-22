package io.newm.sharedfeatures.screens.auth.welcome

import androidx.compose.runtime.Composable
import me.tatarka.inject.annotations.Inject

@Inject
actual class SocialLoginManagerImpl : SocialLoginManager {
    @Composable
    actual override fun rememberGoogleSignInLauncher(onResult: (GoogleSignInResult) -> Unit): () -> Unit = {}
}
