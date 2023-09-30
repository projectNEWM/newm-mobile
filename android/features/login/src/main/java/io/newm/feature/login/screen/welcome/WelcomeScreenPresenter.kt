package io.newm.feature.login.screen.welcome

import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.feature.login.screen.HomeScreen
import io.newm.feature.login.screen.LoginScreen
import io.newm.feature.login.screen.authproviders.google.GoogleSignInLauncher
import io.newm.feature.login.screen.createaccount.CreateAccountScreen
import io.newm.shared.login.repository.KMMException
import io.newm.shared.login.repository.LogInRepository
import io.newm.shared.login.repository.OAuthData

class WelcomeScreenPresenter(
    private val navigator: Navigator,
    private val repository: LogInRepository,
    private val googleSignInLauncher: GoogleSignInLauncher,
    private val activityResultContract: ActivityResultContract<Intent, ActivityResult>,
) : Presenter<WelcomeScreenUiState> {

    @Composable
    override fun present(): WelcomeScreenUiState {
        val launchGoogleSignIn = rememberGoogleSignInLauncher()

        return WelcomeScreenUiState { event ->
            when (event) {
                WelcomeScreenUiEvent.CreateAccountClicked -> navigator.goTo(CreateAccountScreen)
                WelcomeScreenUiEvent.LoginClicked -> navigator.goTo(LoginScreen)
                WelcomeScreenUiEvent.OnGoogleSignInClicked -> launchGoogleSignIn()
            }
        }
    }

    @Composable
    private fun rememberGoogleSignInLauncher(): () -> Unit {
        var result by remember { mutableStateOf<Task<GoogleSignInAccount>?>(null) }

        LaunchedEffect(result) {
            result?.let { task ->
                try {
                    val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                    val idToken = account.idToken
                    idToken ?: throw IllegalStateException("Google sign in failed. idToken is null")

                    repository.oAuthLogin(OAuthData.Google(idToken))
                    navigator.goTo(HomeScreen)
                } catch (e: ApiException) {
                    // The ApiException status code indicates the detailed failure reason.
                    // Please refer to the GoogleSignInStatusCodes class reference for more information.
                    e.printStackTrace()
                } catch (kmmException: KMMException) {
                    kmmException.printStackTrace()
                }
            }
        }

        val activityResultLauncher =
            rememberLauncherForActivityResult(activityResultContract) { activityResult ->
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
                result = task
            }

        return remember { { googleSignInLauncher.launch(activityResultLauncher) } }
    }
}

