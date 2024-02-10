package io.newm.wallet.connect

import android.app.Application
import com.walletconnect.android.Core
import com.walletconnect.android.CoreClient
import com.walletconnect.android.relay.ConnectionType
import com.walletconnect.web3.modal.client.Modal
import com.walletconnect.web3.modal.client.Web3Modal
import com.walletconnect.web3.modal.presets.Web3ModalChainsPresets
import io.newm.BuildConfig
import io.newm.shared.public.constants.WalletConnectConstants
import timber.log.Timber

class WalletConnectInitializer(private val application: Application) {
    private val TAG = "WalletConnectInitializer"
    fun initialize() {
        initializeCoreClient()
        initializeWeb3Modal()
    }

    private fun initializeCoreClient() {
        val connectionType = ConnectionType.AUTOMATIC
        val relayUrl = "relay.walletconnect.com"
        val serverUrl = "wss://$relayUrl?projectId=${BuildConfig.WALLET_CONNECT_PROJECT_ID}"
        val appMetaData = Core.Model.AppMetaData(
            name = "NEWM",
            description = "Link your wallet to NEWM",
            url = "https://newm.io/",
            icons = listOf("https://developers.cardano.org/img/devblog/newm.png"),
            redirect = "kotlin-web3modal://request"
        )

        CoreClient.initialize(
            metaData = appMetaData,
            relayServerUrl = serverUrl,
            connectionType = connectionType,
            application = application,
            onError = {
                Timber.tag(TAG).d("CoreClient initialized unsuccessfully: $it")
            }
        )
    }

    private fun initializeWeb3Modal() {
        Web3Modal.initialize(
            init = Modal.Params.Init(
                core = CoreClient,
                recommendedWalletsIds = WalletConnectConstants.getSupportedWallets()
            ),
            onSuccess = {
                Timber.tag(TAG).d("Web3Modal initialized successfully")
            },
            onError = { error ->
                Timber.tag(TAG).d("Web3Modal initialization error: $error")
            }
        )
        Web3Modal.setDelegate(WalletConnectModalDelegate)
        Web3Modal.setChains(Web3ModalChainsPresets.ethChains.values.toList())
    }
}

