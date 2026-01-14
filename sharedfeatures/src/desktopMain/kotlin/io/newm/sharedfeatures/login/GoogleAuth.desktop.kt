package io.newm.sharedfeatures.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.submitForm
import io.ktor.http.Parameters
import io.ktor.serialization.kotlinx.json.json
import io.newm.shared.ExposedConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.awt.Desktop
import java.net.ServerSocket
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
actual fun rememberGoogleSignInLauncher(onResult: (Result<GoogleUser>) -> Unit): GoogleSignInLauncher {
    val coroutineScope = rememberCoroutineScope()
    return remember(coroutineScope, onResult) {
        DesktopGoogleSignInLauncher(coroutineScope, onResult)
    }
}

class DesktopGoogleSignInLauncher(
    private val coroutineScope: CoroutineScope,
    private val onResult: (Result<GoogleUser>) -> Unit
) : GoogleSignInLauncher {

    override fun launch() {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                println("DesktopGoogleSignInLauncher: Starting sign in flow")
                signIn()
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(Result.failure(e))
            }
        }
    }

    private suspend fun signIn() {
        println("DesktopGoogleSignInLauncher: Setting up server socket")
        val serverSocket = ServerSocket(0)
        val port = serverSocket.localPort
        val redirectUri = "http://127.0.0.1:$port"
        val state = "state-${System.currentTimeMillis()}" 

        val encodedRedirectUri = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8.toString())
        val encodedScope = URLEncoder.encode("email profile openid", StandardCharsets.UTF_8.toString())
        val encodedState = URLEncoder.encode(state, StandardCharsets.UTF_8.toString())
        val encodedClientId = URLEncoder.encode(ExposedConfig.GOOGLE_AUTH_CLIENT_ID, StandardCharsets.UTF_8.toString())

        val authUrl = "https://accounts.google.com/o/oauth2/v2/auth?" +
                "client_id=$encodedClientId&" +
                "redirect_uri=$encodedRedirectUri&" +
                "response_type=code&" +
                "scope=$encodedScope&" +
                "state=$encodedState"

        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                println("DesktopGoogleSignInLauncher: Opening browser to $authUrl")
                Desktop.getDesktop().browse(URI(authUrl))
            } else {
                throw RuntimeException("Desktop browsing not supported")
            }

            println("DesktopGoogleSignInLauncher: Waiting for callback on port $port")
            val socket = serverSocket.accept()
            println("DesktopGoogleSignInLauncher: Connection accepted")
            socket.use {
                val inputStream = it.getInputStream()
                val reader = inputStream.bufferedReader()
                val requestLine = reader.readLine()

                if (requestLine != null && requestLine.startsWith("GET")) {
                    val parts = requestLine.split(" ")
                    if (parts.size > 1) {
                        val path = parts[1]
                        if (path.contains("code=")) {
                            val code = path.substringAfter("code=").substringBefore("&")
                            
                            val response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n<html><body><h2>Login Successful</h2><p>You can close this tab now.</p><script>window.close();</script></body></html>"
                            it.getOutputStream().write(response.toByteArray(StandardCharsets.UTF_8))
                            
                            exchangeCodeForToken(code, redirectUri)
                        } else {
                             // Handle error or cancel
                             val response = "HTTP/1.1 400 Bad Request\r\nContent-Type: text/html\r\n\r\n<html><body><h2>Login Failed</h2></body></html>"
                             it.getOutputStream().write(response.toByteArray(StandardCharsets.UTF_8))
                        }
                    }
                }
            }
        } finally {
            serverSocket.close()
        }
    }

    private suspend fun exchangeCodeForToken(code: String, redirectUri: String) {
        val client = HttpClient(CIO) {
             install(ContentNegotiation) {
                 json(Json { ignoreUnknownKeys = true })
             }
        }
        
        try {
            val response: GoogleTokenResponse = client.submitForm(
                url = "https://oauth2.googleapis.com/token",
                formParameters = Parameters.build {
                    append("client_id", ExposedConfig.GOOGLE_AUTH_CLIENT_ID)
                    append("code", code)
                    append("grant_type", "authorization_code")
                    append("redirect_uri", redirectUri)
                }
            ).body()

            onResult(Result.success(GoogleUser(response.idToken)))
        } finally {
            client.close()
        }
    }
}

@Serializable
data class GoogleTokenResponse(
    @SerialName("id_token") val idToken: String,
    @SerialName("access_token") val accessToken: String,
    @SerialName("expires_in") val expiresIn: Int,
    @SerialName("token_type") val tokenType: String,
    @SerialName("scope") val scope: String,
    @SerialName("refresh_token") val refreshToken: String? = null
)
