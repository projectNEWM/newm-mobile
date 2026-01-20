package shared

import io.newm.shared.commonInternal.TokenManager
import io.newm.shared.commonInternal.db.PreferencesDataStore
import io.newm.shared.commonInternal.services.db.NewmDatabaseWrapper
import kotlinx.coroutines.CoroutineScope

expect interface OSDependencyProvider {
    val preferencesDataStore: PreferencesDataStore
    val tokenManager: TokenManager
    val db: NewmDatabaseWrapper
    val coroutineScope: CoroutineScope
}
