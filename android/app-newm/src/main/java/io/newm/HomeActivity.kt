package io.newm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.retained.LocalRetainedStateRegistry
import com.slack.circuit.retained.continuityRetainedStateRegistry
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import com.walletconnect.web3.modal.client.Web3Modal
import io.newm.core.theme.NewmTheme
import io.newm.screens.Screen
import io.newm.screens.account.UserAccountPresenter
import io.newm.screens.account.UserAccountState
import io.newm.screens.account.UserAccountUi
import io.newm.utils.ui
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

@Suppress("UnstableApiUsage")
class HomeActivity : ComponentActivity() {
    private val circuit: Circuit = createCircuit()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Web3Modal.register(this)
        setContent {
            NewmTheme(darkTheme = true) {
                CircuitDependencies {
                    NewmApp()
                }
            }
        }
    }

    private fun createCircuit(): Circuit {
        return Circuit.Builder()
            .addPresenterFactory(buildPresenterFactory())
            .addUiFactory(buildUiFactory())
            .build()
    }

    private fun buildUiFactory(): Ui.Factory {
        return Ui.Factory { screen, _ ->
            when (screen) {
                is Screen.UserAccount -> ui<UserAccountState> { state, modifier ->
                    UserAccountUi(
                        state = state,
                        modifier = modifier
                    )
                }

                else -> null

            }
        }
    }

    private fun buildPresenterFactory(): Presenter.Factory {
        return Presenter.Factory { screen, navigator, _ ->
            when (screen) {
                is Screen.UserAccount -> inject<UserAccountPresenter> {
                    parametersOf(
                        navigator
                    )
                }.value

                else -> null
            }
        }
    }

    @Composable
    fun CircuitDependencies(
        content: @Composable () -> Unit
    ) {
        CircuitCompositionLocals(circuit) {
            CompositionLocalProvider(LocalRetainedStateRegistry provides continuityRetainedStateRegistry()) {
                content()
            }
        }
    }
}
