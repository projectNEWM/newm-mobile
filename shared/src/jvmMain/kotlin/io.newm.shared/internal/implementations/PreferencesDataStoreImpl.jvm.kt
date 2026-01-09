package io.newm.shared.internal.implementations

import io.newm.shared.commonInternal.db.PreferencesDataStore
import java.util.concurrent.ConcurrentHashMap

class PreferencesDataStoreImpl : PreferencesDataStore {
    private val storage = ConcurrentHashMap<String, Any>()

    override fun saveString(key: String, value: String) {
        storage[key] = value
    }

    override fun getString(key: String): String? {
        return storage[key] as? String
    }

    override fun saveInt(key: String, value: Int) {
        storage[key] = value
    }

    override fun getInt(key: String): Int? {
        return storage[key] as? Int
    }

    override fun saveBoolean(key: String, value: Boolean) {
        storage[key] = value
    }

    override fun getBoolean(key: String): Boolean? {
        return storage[key] as? Boolean
    }

    override fun deleteValue(key: String) {
        storage.remove(key)
    }

    override fun clearAll() {
        storage.clear()
    }
}