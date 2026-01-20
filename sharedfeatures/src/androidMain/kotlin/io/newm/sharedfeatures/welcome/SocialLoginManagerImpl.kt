package io.newm.sharedfeatures.welcome

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import io.newm.shared.config.NewmSharedBuildConfig
import me.tatarka.inject.annotations.Inject

@Inject
actual class SocialLoginManagerImpl(
    private val sharedBuildConfig: NewmSharedBuildConfig,
) : SocialLoginManager {
    @Composable
    actual override fun rememberGoogleSignInLauncher(onResult: (GoogleSignInResult) -> Unit): () -> Unit {
        val context = LocalContext.current

        val launcher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult(),
            ) { result ->
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                    val idToken = account.idToken
                    if (idToken != null) {
                        onResult(GoogleSignInResult.Success(idToken))
                    } else {
                        onResult(
                            GoogleSignInResult.Failure(
                                IllegalStateException("Google sign in failed. idToken is null"),
                            ),
                        )
                    }
                } catch (e: ApiException) {
                    onResult(GoogleSignInResult.Failure(e))
                }
            }

        return remember {
            {
                val gso =
                    GoogleSignInOptions
                        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(sharedBuildConfig.googleAuthClientId)
                        .requestEmail()
                        .build()

                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)
            }
        }
    }
}
