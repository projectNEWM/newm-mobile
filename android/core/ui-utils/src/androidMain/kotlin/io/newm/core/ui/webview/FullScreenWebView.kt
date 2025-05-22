package io.newm.core.ui.webview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun FullScreenWebView(
    context: Context,
    url: String,
    accessToken: String? = null,
    refreshToken: String? = null
) {
    AndroidView(
        factory = { ctx ->
            WebView(ctx).apply {
                val cookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                cookieManager.setAcceptThirdPartyCookies(this, true)

                val baseUrl = url.toUri() // Prefer Uri.parse() for full URLs
                val domain = baseUrl.host ?: "newm.studio" // fallback if parsing fails

                // Set cookies only if they exist
                accessToken?.let {
                    val accessCookie =
                        "accessToken=$it; path=/; domain=$domain"
                    cookieManager.setCookie("https://$domain", accessCookie)
                }
                refreshToken?.let {
                    val refreshCookie =
                        "refreshToken=$it; path=/; domain=$domain"
                    cookieManager.setCookie("https://$domain", refreshCookie)
                }

                cookieManager.flush() // Ensure cookies are written immediately

                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        val currentUrl = request?.url.toString()
                        return if (currentUrl.startsWith("https://newm.studio/") || currentUrl.startsWith("https://newm.io/")) {
                            // Load the URL in the current WebView
                            false
                        } else {
                            // Open external links
                            launchExternalUrl(context, currentUrl)
                            true
                        }
                    }
                }

                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true

                loadUrl(url)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

fun launchExternalUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    context.startActivity(intent)
}
