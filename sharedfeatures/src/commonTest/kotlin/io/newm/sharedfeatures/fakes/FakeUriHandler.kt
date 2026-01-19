package io.newm.sharedfeatures.fakes

import androidx.compose.ui.platform.UriHandler

class FakeUriHandler : UriHandler {
    val openedUris = mutableListOf<String>()
    override fun openUri(uri: String) {
        openedUris.add(uri)
    }
}