package shared

expect class SecureStorage {
    fun store(key: String, value: String)
    fun retrieve(key: String): String?
    fun remove(key: String)
}