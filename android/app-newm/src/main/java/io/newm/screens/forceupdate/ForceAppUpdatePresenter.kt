package io.newm.screens.forceupdate

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter

class ForceAppUpdatePresenter(
    private val navigator: Navigator,
) : Presenter<ForceAppUpdateState> {
    @Composable
    override fun present(): ForceAppUpdateState {
        val context = LocalContext.current

        return ForceAppUpdateState.Content(
            eventSink = { event ->
                when (event) {
                    ForceAppUpdateEvent.OnForceUpdate -> {
                        context.openPlayStore()
                    }
                }
            }
        )
    }

    private fun Context.openPlayStore() {
        val appPackageName = this.packageName
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (e: Exception) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }
}