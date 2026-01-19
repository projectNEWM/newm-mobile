package io.newm.sharedfeatures.fakes

import androidx.compose.runtime.Composable
import io.newm.sharedfeatures.welcome.GoogleSignInResult
import io.newm.sharedfeatures.welcome.SocialLoginManager

class FakeSocialLoginManager : SocialLoginManager {
    var capturedOnResult: ((GoogleSignInResult) -> Unit)? = null
    var launchCalled = false
    var resultToEmit: GoogleSignInResult? = null

    @Composable
    override fun rememberGoogleSignInLauncher(onResult: (GoogleSignInResult) -> Unit): () -> Unit {
        capturedOnResult = onResult
        return {
            launchCalled = true
            resultToEmit?.let { onResult(it) }
        }
    }
}
