package io.newm.shared.internal.implementations

import io.newm.shared.commonInternal.db.PreferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

/**
 * JVM (Desktop) implementation of PreferencesDataStore with file persistence. Preferences are
 * stored in a JSON file in the user's home directory. Thread-safe with mutex protection for
 * concurrent access.
 */
class PreferencesDataStoreImpl : PreferencesDataStore {
    private val mutex = Mutex()
    private val json =
        Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }

    private val preferencesFile: File by lazy {
        val appDir = File(System.getProperty("user.home"), ".newm")
        if (!appDir.exists()) {
            appDir.mkdirs()
        }
        File(appDir, "preferences.json")
    }

    @Serializable
    private data class PreferencesData(
        val strings: Map<String, String> = emptyMap(),
        val ints: Map<String, Int> = emptyMap(),
        val longs: Map<String, Long> = emptyMap(),
        val booleans: Map<String, Boolean> = emptyMap(),
    )

    private suspend fun loadPreferences(): PreferencesData =
        withContext(Dispatchers.IO) {
            try {
                if (preferencesFile.exists()) {
                    val content = preferencesFile.readText()
                    if (content.isNotBlank()) {
                        json.decodeFromString<PreferencesData>(content)
                    } else {
                        PreferencesData()
                    }
                } else {
                    PreferencesData()
                }
            } catch (e: Exception) {
                // If file is corrupted, start fresh
                PreferencesData()
            }
        }

    private suspend fun savePreferences(data: PreferencesData) {
        withContext(Dispatchers.IO) {
            try {
                preferencesFile.writeText(json.encodeToString(data))
            } catch (e: Exception) {
                // Log error but don't crash - preferences are non-critical
                System.err.println("Failed to save preferences: ${e.message}")
            }
        }
    }

    override suspend fun saveString(
        key: String,
        value: String,
    ) {
        mutex.withLock {
            val current = loadPreferences()
            val updated = current.copy(strings = current.strings + (key to value))
            savePreferences(updated)
        }
    }

    override suspend fun getString(key: String): String? = mutex.withLock { loadPreferences().strings[key] }

    override suspend fun saveInt(
        key: String,
        value: Int,
    ) {
        mutex.withLock {
            val current = loadPreferences()
            val updated = current.copy(ints = current.ints + (key to value))
            savePreferences(updated)
        }
    }

    override suspend fun getInt(key: String): Int? = mutex.withLock { loadPreferences().ints[key] }

    override suspend fun saveLong(
        key: String,
        value: Long,
    ) {
        mutex.withLock {
            val current = loadPreferences()
            val updated = current.copy(longs = current.longs + (key to value))
            savePreferences(updated)
        }
    }

    override suspend fun getLong(key: String): Long? = mutex.withLock { loadPreferences().longs[key] }

    override suspend fun saveBoolean(
        key: String,
        value: Boolean,
    ) {
        mutex.withLock {
            val current = loadPreferences()
            val updated = current.copy(booleans = current.booleans + (key to value))
            savePreferences(updated)
        }
    }

    override suspend fun getBoolean(key: String): Boolean? = mutex.withLock { loadPreferences().booleans[key] }

    override suspend fun deleteValue(key: String) {
        mutex.withLock {
            val current = loadPreferences()
            val updated =
                current.copy(
                    strings = current.strings - key,
                    ints = current.ints - key,
                    longs = current.longs - key,
                    booleans = current.booleans - key,
                )
            savePreferences(updated)
        }
    }

    override suspend fun clearAll() {
        mutex.withLock { savePreferences(PreferencesData()) }
    }
}
