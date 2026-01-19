package newm

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import newm.inject.InjectDesktopApplicationComponent
import newm.inject.InjectWindowComponent

fun main() = application {
    val appComponent = InjectDesktopApplicationComponent()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Newm",
    ) {
        val windowComponent = remember(appComponent) { InjectWindowComponent(appComponent) }

        App(
            circuit = windowComponent.circuit,
            config = windowComponent.config,
            onRootPop = { exitApplication() }
        )
    }
}