package io.newm.shared.internal.implementations


import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import io.newm.shared.internal.TokenManager

internal class TokenManagerImpl(
    context: Context,
    private val accountManager: AccountManager
) : TokenManager {

    private val accountType = "${context.packageName}.account"

    override fun getAccessToken(): String? {
        return getAccount()?.let { accountManager.peekAuthToken(it, ACCESS_TOKEN_KEY) }
    }

    override fun getRefreshToken(): String? {
        return getAccount()?.let { accountManager.peekAuthToken(it, REFRESH_TOKEN_KEY) }
    }

    override fun clearToken() {
        getAccount()?.let { accountManager.removeAccountExplicitly(it) }
    }

    override fun setAuthTokens(accessToken: String, refreshToken: String) {
        val account = getAccount() ?: Account(ACCOUNT_NAME, accountType).apply {
            if (!accountManager.addAccountExplicitly(this, null, null)) {
                throw IllegalStateException("Failed to create account")
            }
        }
        accountManager.setAuthToken(account, ACCESS_TOKEN_KEY, accessToken)
        accountManager.setAuthToken(account, REFRESH_TOKEN_KEY, refreshToken)
    }

    private fun getAccount(): Account? =
        accountManager.getAccountsByType(accountType).firstOrNull()

    companion object {
        private const val REFRESH_TOKEN_KEY = "refreshToken"
        private const val ACCESS_TOKEN_KEY = "accessToken"
        private const val ACCOUNT_NAME = "NEWM"
    }

}