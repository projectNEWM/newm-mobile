package io.newm.shared.internal.implementations

import com.liftric.kvault.KVault
import io.newm.shared.commonInternal.db.PreferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

/**
 * iOS implementation of PreferencesDataStore using KVault (Keychain wrapper).
 * Operations are wrapped in IO dispatcher for non-blocking behavior.
 */
class PreferencesDataStoreImpl : PreferencesDataStore {
    private val vault: KVault = KVault("newm_ios_preferences")

    override suspend fun saveString(key: String, value: String) {
        withContext(Dispatchers.IO) {
            vault.set(key, value)
        }
    }

    override suspend fun getString(key: String): String? {
        return withContext(Dispatchers.IO) {
            vault.string(key)
        }
    }

    override suspend fun saveInt(key: String, value: Int) {
        withContext(Dispatchers.IO) {
            vault.set(key, value)
        }
    }

    override suspend fun getInt(key: String): Int? {
        return withContext(Dispatchers.IO) {
            vault.int(key)
        }
    }

    override suspend fun saveLong(key: String, value: Long) {
        withContext(Dispatchers.IO) {
            vault.set(key, value)
        }
    }

    override suspend fun getLong(key: String): Long? {
        return withContext(Dispatchers.IO) {
            vault.long(key)
        }
    }

    override suspend fun saveBoolean(key: String, value: Boolean) {
        withContext(Dispatchers.IO) {
            vault.set(key, value)
        }
    }

    override suspend fun getBoolean(key: String): Boolean? {
        return withContext(Dispatchers.IO) {
            vault.bool(key)
        }
    }

    override suspend fun deleteValue(key: String) {
        withContext(Dispatchers.IO) {
            vault.deleteObject(key)
        }
    }

    override suspend fun clearAll() {
        withContext(Dispatchers.IO) {
            vault.clear()
        }
    }
}