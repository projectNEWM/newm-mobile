@file:OptIn(ExperimentalCoroutinesApi::class)

package io.newm.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.newm.shared.models.Song
import io.newm.shared.usecases.WalletConnectUseCase
import io.newm.shared.usecases.WalletNFTSongsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NFTLibraryViewModel(
    private val useCase: WalletNFTSongsUseCase,
    private val walletConnectManager: WalletConnectUseCase
) : ViewModel() {

    private val xPub: MutableStateFlow<String?> = MutableStateFlow(null)

    private var _state: StateFlow<NFTLibraryState> = xPub.filterNotNull().flatMapLatest { xPub ->
        useCase.getAllWalletNFTSongs(xPub).mapLatest {
            NFTLibraryState.Content(it)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = NFTLibraryState.Loading
    )

    val state: StateFlow<NFTLibraryState>
        get() = _state

    fun setXPub(xPub: String) {
        this.xPub.value = xPub
        if(!walletConnectManager.isConnected()) {
            walletConnectManager.connect(xPub)
        }

        viewModelScope.launch {
            useCase.getWalletNFTs()
        }
    }
}

sealed interface NFTLibraryState {
    data object Loading : NFTLibraryState
    data class Content(val songs: List<Song>) : NFTLibraryState
}

