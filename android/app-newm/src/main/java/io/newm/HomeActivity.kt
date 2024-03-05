package io.newm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.walletconnect.web3.modal.client.Web3Modal
import io.newm.core.theme.NewmTheme

@Suppress("UnstableApiUsage")
class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Web3Modal.register(this)
        setContent {
            NewmTheme(darkTheme = true) {
                NewmApp()
            }
        }
    }
}
