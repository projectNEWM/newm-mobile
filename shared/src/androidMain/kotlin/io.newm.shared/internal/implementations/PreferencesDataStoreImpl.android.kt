package io.newm.shared.internal.implementations

import io.newm.shared.internal.db.PreferencesDataStore
import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

private val Context.dataStore by preferencesDataStore(name = "settings")

class PreferencesDataStoreImpl(private val context: Context) : PreferencesDataStore {

    override fun saveString(key: String, value: String) {
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[stringPreferencesKey(key)] = value
            }
        }
    }

    override fun getString(key: String): String? {
        return runBlocking {
            val preferences = context.dataStore.data.first()
            preferences[stringPreferencesKey(key)]
        }
    }

    override fun saveInt(key: String, value: Int) {
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[intPreferencesKey(key)] = value
            }
        }
    }

    override fun getInt(key: String): Int? {
        return runBlocking {
            val preferences = context.dataStore.data.first()
            preferences[intPreferencesKey(key)]
        }
    }

    override fun saveBoolean(key: String, value: Boolean) {
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[booleanPreferencesKey(key)] = value
            }
        }
    }

    override fun getBoolean(key: String): Boolean? {
        return runBlocking {
            val preferences = context.dataStore.data.first()
            preferences[booleanPreferencesKey(key)]
        }
    }

    override fun deleteValue(key: String) {
        runBlocking {
            context.dataStore.edit { preferences ->
                // Assuming string key for simplicity
                preferences.remove(stringPreferencesKey(key))
            }
        }
    }
}