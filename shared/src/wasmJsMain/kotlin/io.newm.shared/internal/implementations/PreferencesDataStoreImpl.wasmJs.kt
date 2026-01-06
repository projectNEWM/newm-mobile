package io.newm.shared.internal.implementations

import io.newm.shared.commonInternal.db.PreferencesDataStore
import kotlinx.browser.window

val localStorage get() = window.localStorage

class PreferencesDataStoreImpl : PreferencesDataStore {

    override fun saveString(key: String, value: String) {
        localStorage.setItem(key, value)
    }

    override fun getString(key: String): String? {
        return localStorage.getItem(key)
    }

    override fun saveInt(key: String, value: Int) {
        localStorage.setItem(key, value.toString())
    }

    override fun getInt(key: String): Int? {
        return localStorage.getItem(key)?.toIntOrNull()
    }

    override fun saveBoolean(key: String, value: Boolean) {
        localStorage.setItem(key, value.toString())
    }

    override fun getBoolean(key: String): Boolean? {
        return localStorage.getItem(key)?.toBooleanStrictOrNull()
    }

    override fun deleteValue(key: String) {
        localStorage.removeItem(key)
    }

    override fun clearAll() {
        localStorage.clear()
    }
}