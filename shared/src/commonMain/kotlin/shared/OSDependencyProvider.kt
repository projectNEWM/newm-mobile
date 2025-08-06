package shared

import io.newm.shared.commonInternal.db.PreferencesDataStore

expect interface OSDependencyProvider{
    val preferencesDataStore: PreferencesDataStore
}