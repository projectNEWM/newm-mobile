package io.newm.sharedfeatures.login

import android.content.Context
import android.content.Intent
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
import io.newm.shared.commonPublic.models.error.KMMException

@Composable
actual fun rememberGoogleSignInLauncher(onResult: (Result<GoogleUser>) -> Unit): GoogleSignInLauncher {
    val context = LocalContext.current
    
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                onResult(Result.success(GoogleUser(idToken)))
            } else {
                onResult(Result.failure(IllegalStateException("Google sign in failed. idToken is null")))
            }
        } catch (e: ApiException) {
            onResult(Result.failure(e))
        } catch (e: Exception) {
             onResult(Result.failure(e))
        }
    }

    return remember(launcher, context) {
        AndroidGoogleSignInLauncher(context, launcher)
    }
}

class AndroidGoogleSignInLauncher(
    private val context: Context,
    private val launcher: androidx.activity.result.ActivityResultLauncher<Intent>
) : GoogleSignInLauncher {
    override fun launch() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(io.newm.shared.ExposedConfig.GOOGLE_AUTH_CLIENT_ID)
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }
}
