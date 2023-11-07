package io.newm.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.newm.shared.login.repository.KMMException
import io.newm.shared.repositories.NFTTrack
import io.newm.shared.usecases.WalletConnectUseCase
import io.newm.shared.usecases.WalletNFTSongsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NFTLibraryViewModel(
    private val walletConnectUseCase: WalletConnectUseCase,
    private val walletNFTSongsUseCase: WalletNFTSongsUseCase
) : ViewModel() {

    private val _nftLibraryState = MutableStateFlow<NFTLibraryState>(NFTLibraryState.Loading)
    val state: StateFlow<NFTLibraryState> by this::_nftLibraryState

    init {
        fetchNFTLibraryContent()
    }

    private fun fetchNFTLibraryContent() = viewModelScope.launch {
        _nftLibraryState.value = NFTLibraryState.Loading
        if (walletConnectUseCase.isConnected()) {
            try {
                val nftSongs = walletNFTSongsUseCase.getWalletNFTs()
                _nftLibraryState.value = NFTLibraryState.Content(nftSongs)
            } catch (e: KMMException) {
                _nftLibraryState.value =
                    NFTLibraryState.Error(message = e.message ?: "An error occurred")
            } catch (e: Exception) {
                _nftLibraryState.value =
                    NFTLibraryState.Error(e.localizedMessage ?: "An unexpected error occurred")
            }
        } else {
            _nftLibraryState.value = NFTLibraryState.NoWalletFound
        }
    }
}

sealed interface NFTLibraryState {
    object Loading : NFTLibraryState
    object NoWalletFound : NFTLibraryState
    data class Content(val songs: List<NFTTrack>) : NFTLibraryState
    data class Error(val message: String) : NFTLibraryState
}
