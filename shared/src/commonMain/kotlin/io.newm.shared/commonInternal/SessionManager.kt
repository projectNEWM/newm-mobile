package io.newm.shared.commonInternal

import io.newm.shared.commonInternal.db.PreferencesDataStore
import io.newm.shared.commonInternal.services.db.NewmDatabaseWrapper
import me.tatarka.inject.annotations.Inject
import shared.Notification
import shared.postNotification

@Inject
class SessionManager(
    private val tokenManager: TokenManager,
    private val db: NewmDatabaseWrapper,
    private val dataStore: PreferencesDataStore,
) {
    suspend fun logout() {
        tokenManager.clearToken()
        db.clear()
        dataStore.clearAll()
        postNotification(Notification.loginStateChanged)
    }
}
