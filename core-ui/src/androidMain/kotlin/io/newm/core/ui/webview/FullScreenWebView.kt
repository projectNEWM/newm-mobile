package io.newm.core.ui.webview

import android.annotation.SuppressLint
import android.content.Intent
import android.net.http.SslError
import android.webkit.CookieManager
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import io.newm.core.resources.R
import io.newm.core.ui.LoadingScreen

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun FullScreenWebView(
    url: String,
    modifier: Modifier = Modifier,
    accessToken: String? = null,
    refreshToken: String? = null,
) {
    val context = LocalContext.current
    val cookieManager = remember { CookieManager.getInstance() }
    var webView by remember { mutableStateOf<WebView?>(null) }
    var loadRequestId by remember(url) { mutableIntStateOf(0) }
    var lastAppliedLoadRequestId by remember(url) { mutableIntStateOf(-1) }
    var hasLoadedSuccessfully by remember(url) { mutableStateOf(false) }
    var isPageLoading by remember(url) { mutableStateOf(true) }
    var errorType by remember(url) { mutableStateOf<WebViewErrorType?>(null) }

    val retryLabel = stringResource(R.string.web_view_retry)
    val offlineTitle = stringResource(R.string.record_store_error_title)
    val offlineMessage = stringResource(R.string.record_store_error_message)
    val loadErrorTitle = stringResource(R.string.web_view_load_error_title)
    val loadErrorMessage = stringResource(R.string.web_view_load_error_message)

    DisposableEffect(Unit) {
        onDispose {
            webView?.apply {
                stopLoading()
                removeAllViews()
                destroy()
            }
            webView = null
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    webView = this
                    cookieManager.setAcceptCookie(true)
                    cookieManager.setAcceptThirdPartyCookies(this, true)

                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE

                    webViewClient =
                        object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: WebResourceRequest?,
                            ): Boolean {
                                if (request?.isForMainFrame != true) return false
                                val currentUrl = request.url.toString()
                                return if (isInternalUrl(currentUrl)) {
                                    false
                                } else {
                                    launchExternalUrl(context, currentUrl)
                                    true
                                }
                            }

                            override fun onPageStarted(
                                view: WebView?,
                                url: String?,
                                favicon: android.graphics.Bitmap?,
                            ) {
                                errorType = null
                                isPageLoading = true
                            }

                            override fun onPageFinished(
                                view: WebView?,
                                url: String?,
                            ) {
                                if (errorType == null) {
                                    hasLoadedSuccessfully = true
                                    isPageLoading = false
                                }
                            }

                            override fun onReceivedError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                error: WebResourceError?,
                            ) {
                                if (request?.isForMainFrame != true) return
                                isPageLoading = false
                                errorType = error?.toWebViewErrorType() ?: WebViewErrorType.PageLoad
                            }

                            override fun onReceivedHttpError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                errorResponse: WebResourceResponse?,
                            ) {
                                if (request?.isForMainFrame != true) return
                                if ((errorResponse?.statusCode ?: 0) >= 400) {
                                    isPageLoading = false
                                    errorType = WebViewErrorType.PageLoad
                                }
                            }

                            override fun onReceivedSslError(
                                view: WebView?,
                                handler: SslErrorHandler?,
                                error: SslError?,
                            ) {
                                handler?.cancel()
                                isPageLoading = false
                                errorType = WebViewErrorType.PageLoad
                            }
                        }
                }
            },
            update = { view ->
                syncAuthCookies(
                    cookieManager = cookieManager,
                    url = url,
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                )
                if (lastAppliedLoadRequestId != loadRequestId) {
                    lastAppliedLoadRequestId = loadRequestId
                    errorType = null
                    isPageLoading = true
                    view.stopLoading()
                    view.loadUrl(url)
                }
            },
            modifier = Modifier.fillMaxSize(),
        )

        if (isPageLoading && !hasLoadedSuccessfully) {
            LoadingScreen()
        }

        errorType?.let { webViewErrorType ->
            WebViewErrorScreen(
                title =
                    when (webViewErrorType) {
                        WebViewErrorType.Offline -> offlineTitle
                        WebViewErrorType.PageLoad -> loadErrorTitle
                    },
                message =
                    when (webViewErrorType) {
                        WebViewErrorType.Offline -> offlineMessage
                        WebViewErrorType.PageLoad -> loadErrorMessage
                    },
                actionLabel = retryLabel,
                onAction = { loadRequestId += 1 },
            )
        }
    }
}

private fun syncAuthCookies(
    cookieManager: CookieManager,
    url: String,
    accessToken: String?,
    refreshToken: String?,
) {
    val domain = url.toUri().host ?: return

    accessToken?.let {
        cookieManager.setCookie(
            "https://$domain",
            "accessToken=$it; path=/; domain=$domain; Secure; SameSite=Lax",
        )
    }
    refreshToken?.let {
        cookieManager.setCookie(
            "https://$domain",
            "refreshToken=$it; path=/; domain=$domain; Secure; SameSite=Lax",
        )
    }
    cookieManager.flush()
}

private fun WebResourceError.toWebViewErrorType(): WebViewErrorType =
    when (errorCode) {
        WebViewClient.ERROR_HOST_LOOKUP,
        WebViewClient.ERROR_CONNECT,
        WebViewClient.ERROR_TIMEOUT,
        WebViewClient.ERROR_IO,
        WebViewClient.ERROR_PROXY_AUTHENTICATION,
        WebViewClient.ERROR_UNKNOWN,
        -> WebViewErrorType.Offline

        else -> WebViewErrorType.PageLoad
    }

private enum class WebViewErrorType {
    Offline,
    PageLoad,
}

fun launchExternalUrl(
    context: android.content.Context,
    url: String,
) {
    runCatching {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        context.startActivity(intent)
    }
}
