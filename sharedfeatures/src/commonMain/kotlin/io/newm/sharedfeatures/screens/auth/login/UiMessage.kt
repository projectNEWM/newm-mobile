package io.newm.sharedfeatures.screens.auth.login

import org.jetbrains.compose.resources.StringResource

sealed interface UiMessage {
    data class Resource(
        val resId: StringResource,
    ) : UiMessage

    data class Text(
        val text: String,
    ) : UiMessage
}
