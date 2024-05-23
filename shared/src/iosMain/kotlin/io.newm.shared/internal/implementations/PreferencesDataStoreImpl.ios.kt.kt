package io.newm.shared.internal.implementations

import io.newm.shared.internal.db.PreferencesDataStore
import platform.Foundation.NSUserDefaults

class PreferencesDataStoreImpl : PreferencesDataStore {

    private val userDefaults = NSUserDefaults.standardUserDefaults

    override fun saveString(key: String, value: String) {
        userDefaults.setObject(value, forKey = key)
    }

    override fun getString(key: String): String? {
        return userDefaults.stringForKey(key)
    }

    override fun saveInt(key: String, value: Int) {
        userDefaults.setInteger(value.toLong(), forKey = key)
    }

    override fun getInt(key: String): Int? {
        val value = userDefaults.integerForKey(key)
        return if (value.toInt() == 0 && userDefaults.objectForKey(key) == null) null else value.toInt()
    }

    override fun saveBoolean(key: String, value: Boolean) {
        userDefaults.setBool(value, forKey = key)
    }

    override fun getBoolean(key: String): Boolean? {
        val value = userDefaults.boolForKey(key)
        return if (!value && userDefaults.objectForKey(key) == null) null else value
    }

    override fun deleteValue(key: String) {
        userDefaults.removeObjectForKey(key)
    }
}