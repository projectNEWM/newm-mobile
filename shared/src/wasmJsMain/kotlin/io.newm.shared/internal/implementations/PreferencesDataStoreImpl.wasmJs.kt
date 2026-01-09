package io.newm.shared.internal.implementations

import io.newm.shared.commonInternal.db.PreferencesDataStore
import kotlinx.browser.window

/**
 * WebAssembly (Browser) implementation of PreferencesDataStore using localStorage.
 *
 * Note: Browser localStorage is synchronous and limited to ~5MB.
 * For larger storage needs, consider migrating to IndexedDB.
 * All operations complete immediately in the browser's single-threaded environment.
 */
class PreferencesDataStoreImpl : PreferencesDataStore {

    private val localStorage get() = window.localStorage

    // Prefix keys by type to avoid conflicts when the same key is used for different types
    private fun stringKey(key: String) = "str_$key"
    private fun intKey(key: String) = "int_$key"
    private fun longKey(key: String) = "long_$key"
    private fun boolKey(key: String) = "bool_$key"

    override suspend fun saveString(key: String, value: String) {
        localStorage.setItem(stringKey(key), value)
    }

    override suspend fun getString(key: String): String? {
        return localStorage.getItem(stringKey(key))
    }

    override suspend fun saveInt(key: String, value: Int) {
        localStorage.setItem(intKey(key), value.toString())
    }

    override suspend fun getInt(key: String): Int? {
        return localStorage.getItem(intKey(key))?.toIntOrNull()
    }

    override suspend fun saveLong(key: String, value: Long) {
        localStorage.setItem(longKey(key), value.toString())
    }

    override suspend fun getLong(key: String): Long? {
        return localStorage.getItem(longKey(key))?.toLongOrNull()
    }

    override suspend fun saveBoolean(key: String, value: Boolean) {
        localStorage.setItem(boolKey(key), value.toString())
    }

    override suspend fun getBoolean(key: String): Boolean? {
        return localStorage.getItem(boolKey(key))?.toBooleanStrictOrNull()
    }

    override suspend fun deleteValue(key: String) {
        // Remove all type-prefixed versions of the key
        localStorage.removeItem(stringKey(key))
        localStorage.removeItem(intKey(key))
        localStorage.removeItem(longKey(key))
        localStorage.removeItem(boolKey(key))
    }

    override suspend fun clearAll() {
        // Only clear NEWM-related keys to avoid affecting other apps
        val keysToRemove = mutableListOf<String>()
        for (i in 0 until localStorage.length) {
            val key = localStorage.key(i)
            if (key != null && (key.startsWith("str_") || key.startsWith("int_") ||
                        key.startsWith("long_") || key.startsWith("bool_"))) {
                keysToRemove.add(key)
            }
        }
        keysToRemove.forEach { localStorage.removeItem(it) }
    }
}