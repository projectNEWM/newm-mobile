package io.newm.sharedfeatures.welcome

import androidx.compose.runtime.Composable

interface SocialLoginManager {
    @Composable
    fun rememberGoogleSignInLauncher(
        onResult: (GoogleSignInResult) -> Unit
    ): () -> Unit
}

sealed interface GoogleSignInResult {
    data class Success(val idToken: String) : GoogleSignInResult
    data class Failure(val error: Throwable) : GoogleSignInResult
}
