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


    private var _state: StateFlow<NFTLibraryState> =
        useCase.getAllWalletNFTSongs().mapLatest {
            if(walletConnectManager.isConnected()){
                NFTLibraryState.Content(it)
            } else {
                NFTLibraryState.NoWalletFound
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = NFTLibraryState.NoWalletFound
        )

    val state: StateFlow<NFTLibraryState>
        get() = _state

//    init {
//        viewModelScope.launch {
//            useCase.getWalletNFTs()
//        }
//    }
}

sealed interface NFTLibraryState {
    data object Loading : NFTLibraryState

    data object NoWalletFound : NFTLibraryState

    data class Content(val songs: List<Song>) : NFTLibraryState
}

