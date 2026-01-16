package shared

import io.newm.shared.commonInternal.db.PreferencesDataStore
import kotlinx.coroutines.CoroutineScope

import io.newm.shared.commonInternal.TokenManager
import io.newm.shared.commonInternal.services.db.NewmDatabaseWrapper

expect interface OSDependencyProvider{
    val preferencesDataStore: PreferencesDataStore
    val tokenManager: TokenManager
    val db: NewmDatabaseWrapper
    val coroutineScope: CoroutineScope
}