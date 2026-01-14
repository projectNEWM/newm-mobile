package io.newm.sharedfeatures.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.newm.shared.ExposedConfig
import kotlinx.browser.window
import org.w3c.dom.MessageEvent
import org.w3c.dom.Window
import kotlin.js.toJsString

private fun getCurrentTimestamp(): Double = js("Date.now()")
private fun encodeURIComponent(uri: String): String = js("encodeURIComponent(uri)")

@Composable
actual fun rememberGoogleSignInLauncher(onResult: (Result<GoogleUser>) -> Unit): GoogleSignInLauncher {
    val hash = window.location.hash
    if (hash.contains("id_token=")) {
        val idToken = hash.substringAfter("id_token=").substringBefore("&")
        val opener = window.opener
        if (opener is Window) {
            opener.postMessage(idToken.toJsString(), window.location.origin)
        }
        window.close()
    }

    return remember(onResult) { WasmGoogleSignInLauncher(onResult) }
}

class WasmGoogleSignInLauncher(
    private val onResult: (Result<GoogleUser>) -> Unit
) : GoogleSignInLauncher {

    override fun launch() {
        val clientId = ExposedConfig.GOOGLE_AUTH_CLIENT_ID
        val redirectUri = window.location.origin
        val timestamp = getCurrentTimestamp()
        val nonce = "nonce-$timestamp"
        
        val encodedClientId = encodeURIComponent(clientId)
        val encodedRedirectUri = encodeURIComponent(redirectUri)
        val encodedScope = encodeURIComponent("openid email profile")
        val encodedNonce = encodeURIComponent(nonce)

        val authUrl = "https://accounts.google.com/o/oauth2/v2/auth?" +
                "client_id=$encodedClientId&" +
                "redirect_uri=$encodedRedirectUri&" +
                "response_type=id_token&" +
                "scope=$encodedScope&" +
                "nonce=$encodedNonce"
        
        window.open(authUrl, "GoogleSignIn", "width=500,height=600")
        
        window.addEventListener("message") { event ->
            if (event is MessageEvent) {
                 if (event.origin == window.location.origin) {
                    val data = event.data
                    if (data != null) {
                         val token = data.toString()
                         if (token.isNotEmpty()) {
                             onResult(Result.success(GoogleUser(token)))
                         }
                    }
                }
            }
        }
    }
}