package io.newm.shared.commonInternal.db

/**
 * Platform-agnostic preferences data store interface. All operations are suspend functions to
 * support non-blocking implementations across different platforms (Android DataStore, iOS KVault,
 * JVM file-based, Wasm localStorage).
 */
interface PreferencesDataStore {
    suspend fun saveString(
        key: String,
        value: String,
    )

    suspend fun getString(key: String): String?

    suspend fun saveInt(
        key: String,
        value: Int,
    )

    suspend fun getInt(key: String): Int?

    suspend fun saveLong(
        key: String,
        value: Long,
    )

    suspend fun getLong(key: String): Long?

    suspend fun saveBoolean(
        key: String,
        value: Boolean,
    )

    suspend fun getBoolean(key: String): Boolean?

    /**
     * Deletes a value for the given key, regardless of its type. Implementations must handle
     * removal of all possible key types.
     */
    suspend fun deleteValue(key: String)

    /** Clears all stored preferences. */
    suspend fun clearAll()
}
