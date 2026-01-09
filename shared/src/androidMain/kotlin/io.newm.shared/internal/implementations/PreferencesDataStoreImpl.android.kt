package io.newm.shared.internal.implementations

import io.newm.shared.commonInternal.db.PreferencesDataStore
import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "settings")

/**
 * Android implementation of PreferencesDataStore using Jetpack DataStore.
 * All operations are non-blocking suspend functions.
 */
class PreferencesDataStoreImpl(private val context: Context) : PreferencesDataStore {

    override suspend fun saveString(key: String, value: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    override suspend fun getString(key: String): String? {
        val preferences = context.dataStore.data.first()
        return preferences[stringPreferencesKey(key)]
    }

    override suspend fun saveInt(key: String, value: Int) {
        context.dataStore.edit { preferences ->
            preferences[intPreferencesKey(key)] = value
        }
    }

    override suspend fun getInt(key: String): Int? {
        val preferences = context.dataStore.data.first()
        return preferences[intPreferencesKey(key)]
    }

    override suspend fun saveLong(key: String, value: Long) {
        context.dataStore.edit { preferences ->
            preferences[longPreferencesKey(key)] = value
        }
    }

    override suspend fun getLong(key: String): Long? {
        val preferences = context.dataStore.data.first()
        return preferences[longPreferencesKey(key)]
    }

    override suspend fun saveBoolean(key: String, value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    override suspend fun getBoolean(key: String): Boolean? {
        val preferences = context.dataStore.data.first()
        return preferences[booleanPreferencesKey(key)]
    }

    override suspend fun deleteValue(key: String) {
        context.dataStore.edit { preferences ->
            // Remove all possible key types to ensure complete deletion
            preferences.remove(stringPreferencesKey(key))
            preferences.remove(intPreferencesKey(key))
            preferences.remove(longPreferencesKey(key))
            preferences.remove(booleanPreferencesKey(key))
            preferences.remove(floatPreferencesKey(key))
            preferences.remove(doublePreferencesKey(key))
        }
    }

    override suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}