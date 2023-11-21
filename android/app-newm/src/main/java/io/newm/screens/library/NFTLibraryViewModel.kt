package io.newm.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.ConnectWalletUseCase
import io.newm.shared.public.usecases.WalletNFTTracksUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NFTLibraryViewModel(
    private val connectWalletUseCase: ConnectWalletUseCase,
    private val walletNFTTracksUseCase: WalletNFTTracksUseCase
) : ViewModel() {

    val state: StateFlow<NFTLibraryState> by lazy {
        connectWalletUseCase.isConnectedFlow().flatMapLatest { isConnected ->
            if (isConnected) {
                try {
                    walletNFTTracksUseCase.getAllNFTTracksFlow().map { nftTracks ->
                        NFTLibraryState.Content(nftTracks)
                    }
                } catch (e: KMMException) {
                    flowOf(NFTLibraryState.Error(message = e.message ?: "An error occurred"))
                } catch (e: Exception) {
                    flowOf(NFTLibraryState.Error(e.localizedMessage ?: "An unexpected error occurred"))
                }

            } else {
                flowOf(NFTLibraryState.NoWalletFound)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), NFTLibraryState.Loading)
    }
}

sealed interface NFTLibraryState {
    data object Loading : NFTLibraryState
    data object NoWalletFound : NFTLibraryState
    data class Content(val nftTracks: List<NFTTrack>) : NFTLibraryState
    data class Error(val message: String) : NFTLibraryState
}
