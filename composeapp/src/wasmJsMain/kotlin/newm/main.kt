package newm

import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window
import newm.inject.InjectWasmActivityComponent
import newm.inject.InjectWasmApplicationComponent

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val appComponent = InjectWasmApplicationComponent()

    ComposeViewport(document.body!!) {
        val activityComponent = remember { InjectWasmActivityComponent(appComponent) }
        App(circuit = activityComponent.circuit, onRootPop = { window.close() })
    }
}