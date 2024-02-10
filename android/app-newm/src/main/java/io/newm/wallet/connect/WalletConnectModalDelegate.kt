package io.newm.wallet.connect

import com.walletconnect.android.Core
import com.walletconnect.android.CoreClient
import com.walletconnect.web3.modal.client.Modal
import com.walletconnect.web3.modal.client.Web3Modal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.core.component.getScopeName
import timber.log.Timber

object WalletConnectModalDelegate : Web3Modal.ModalDelegate, CoreClient.CoreDelegate  {
    private const val TAG = "WalletConnectModalDelegate"
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val _wcEventModels: MutableSharedFlow<Modal.Model?> = MutableSharedFlow()
    val wcEventModels: SharedFlow<Modal.Model?> = _wcEventModels.asSharedFlow()

    private val _coreEvents: MutableSharedFlow<Core.Model> = MutableSharedFlow()
    val coreEvents: SharedFlow<Core.Model> = _coreEvents.asSharedFlow()

    private var selectedSessionTopic: String? = null
        private set

    init {
        Web3Modal.setDelegate(this)
        CoreClient.setDelegate(this)
    }

    // Triggered when receives the session approval from wallet
    override fun onSessionApproved(approvedSession: Modal.Model.ApprovedSession) {
        Timber.tag(TAG).d("onSessionApproved($approvedSession)")
        selectedSessionTopic = approvedSession.getScopeName().getScopeName().value

        scope.launch {
            _wcEventModels.emit(approvedSession)
        }
    }

    // Triggered when receives the session rejection from wallet
    override fun onSessionRejected(rejectedSession: Modal.Model.RejectedSession) {
        Timber.tag(TAG).d("onSessionRejected($rejectedSession)")
        scope.launch {
            _wcEventModels.emit(rejectedSession)
        }
    }

    // Triggered when receives the session update from wallet
    override fun onSessionUpdate(updatedSession: Modal.Model.UpdatedSession) {
        Timber.tag(TAG).d("onSessionUpdate($updatedSession)")
        scope.launch {
            _wcEventModels.emit(updatedSession)
        }
    }

    // Triggered when receives the session extend from wallet
    override fun onSessionExtend(session: Modal.Model.Session) {
        Timber.tag(TAG).d("onSessionExtend($session)")
        scope.launch {
            _wcEventModels.emit(session)
        }
    }

    // Triggered when the peer emits events that match the list of events agreed upon session settlement
    override fun onSessionEvent(sessionEvent: Modal.Model.SessionEvent) {
        Timber.tag(TAG).d("onSessionEvent($sessionEvent)")
        scope.launch {
            _wcEventModels.emit(sessionEvent)
        }
    }

    // Triggered when receives the session delete from wallet
    override fun onSessionDelete(deletedSession: Modal.Model.DeletedSession) {
        Timber.tag(TAG).d("onSessionDelete($deletedSession)")
        deselectAccountDetails()

        scope.launch {
            _wcEventModels.emit(deletedSession)
        }
    }

    // Triggered when receives the session request response from wallet
    override fun onSessionRequestResponse(response: Modal.Model.SessionRequestResponse) {
        Timber.tag(TAG).d("onSessionRequestResponse($response)")
        scope.launch {
            _wcEventModels.emit(response)
        }
    }

    // Triggered when a proposal becomes expired
    override fun onProposalExpired(proposal: Modal.Model.ExpiredProposal) {
        Timber.tag(TAG).d("onProposalExpired($proposal)")
        scope.launch {
            _wcEventModels.emit(proposal)
        }
    }

    // Triggered when a request becomes expired
    override fun onRequestExpired(request: Modal.Model.ExpiredRequest) {
        Timber.tag(TAG).d("onRequestExpired($request)")
        scope.launch {
            _wcEventModels.emit(request)
        }
    }

    private fun deselectAccountDetails() {
        selectedSessionTopic = null
    }

    //Triggered whenever the connection state is changed
    override fun onConnectionStateChange(state: Modal.Model.ConnectionState) {
        Timber.tag(TAG).d("onConnectionStateChange($state)")
        scope.launch {
            _wcEventModels.emit(state)
        }
    }

    // Triggered whenever there is an issue inside the SDK
    override fun onError(error: Modal.Model.Error) {
        Timber.tag(TAG).d(error.throwable.stackTraceToString())
        scope.launch {
            _wcEventModels.emit(error)
        }
    }

    override fun onPairingDelete(deletedPairing: Core.Model.DeletedPairing) {
        Timber.tag(TAG).d("Dapp pairing deleted: $deletedPairing")
    }

    override fun onPairingExpired(expiredPairing: Core.Model.ExpiredPairing) {
        Timber.tag(TAG).d("Dapp pairing expired: $expiredPairing")
        scope.launch {
            _coreEvents.emit(expiredPairing)
        }
    }

    override fun onPairingState(pairingState: Core.Model.PairingState) {
        Timber.tag(TAG).d("Dapp pairing state: $pairingState")
    }
}