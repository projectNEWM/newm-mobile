package io.newm.shared.public.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * This is a hacky way to show a progress indicator
 * until we know for sure that the user has an empty wallet.
 *
 * We need to think about how we want to the handle offline-first functionality
 * that we currently have. The database is the source of truth, but it's empty while
 * the NFTs are loading, so we can't rely on it to show the empty state or loading state.
 */
interface HasEmptyWalletUseCase {
    val hasEmptyWallet: Flow<Boolean>
    fun setHasEmptyWallet(hasEmptyWallet: Boolean)
}

class HasEmptyWalletImpl : HasEmptyWalletUseCase {
    private var _hasEmptyWallet = MutableStateFlow(false)

    override val hasEmptyWallet: Flow<Boolean> = _hasEmptyWallet.asStateFlow()

    override fun setHasEmptyWallet(hasEmptyWallet: Boolean) {
        _hasEmptyWallet.update {
            hasEmptyWallet
        }
    }
}
