package io.newm.shared.internal.implementations

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import io.newm.shared.commonInternal.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject

@Inject
class TokenManagerImpl(
    context: Context,
    private val accountManager: AccountManager
) : TokenManager {

    private val accountType = "${context.packageName}.account"

    override suspend fun getAccessToken(): String? = withContext(Dispatchers.IO) {
        getAccount()?.let { accountManager.peekAuthToken(it, ACCESS_TOKEN_KEY) }
    }

    override suspend fun getRefreshToken(): String? = withContext(Dispatchers.IO) {
        getAccount()?.let { accountManager.peekAuthToken(it, REFRESH_TOKEN_KEY) }
    }

    override suspend fun clearToken() {
        withContext(Dispatchers.IO) {
            getAccount()?.let { accountManager.removeAccountExplicitly(it) }
        }
    }

    override suspend fun setAuthTokens(accessToken: String, refreshToken: String) {
        withContext(Dispatchers.IO) {
            val account = getAccount() ?: Account(ACCOUNT_NAME, accountType).apply {
                if (!accountManager.addAccountExplicitly(this, null, null)) {
                    throw IllegalStateException("Failed to create account")
                }
            }
            accountManager.setAuthToken(account, ACCESS_TOKEN_KEY, accessToken)
            accountManager.setAuthToken(account, REFRESH_TOKEN_KEY, refreshToken)
        }
    }

    private fun getAccount(): Account? =
        accountManager.getAccountsByType(accountType).firstOrNull()

    companion object {
        private const val REFRESH_TOKEN_KEY = "refreshToken"
        private const val ACCESS_TOKEN_KEY = "accessToken"
        private const val ACCOUNT_NAME = "NEWM"
    }

}