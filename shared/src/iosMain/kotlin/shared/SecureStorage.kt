package shared

import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.create
import platform.Foundation.kCFBooleanTrue
import platform.Security.SecItemAdd
import platform.Security.SecItemCopyMatching
import platform.Security.SecItemDelete
import platform.Security.kSecAttrAccount
import platform.Security.kSecAttrService
import platform.Security.kSecClass
import platform.Security.kSecClassGenericPassword
import platform.Security.kSecMatchLimit
import platform.Security.kSecMatchLimitOne
import platform.Security.kSecReturnData
import platform.Security.kSecValueData
import platform.darwin.errSecSuccess

actual class SecureStorage(private val account: String) {
    actual fun store(key: String, value: String) {
        val data = value.encodeToByteArray().toNSData()
        val query = dictOf(
            kSecClass to kSecClassGenericPassword,
            kSecAttrAccount to key,
            kSecValueData to data
        )

        SecItemDelete(query)
        SecItemAdd(query, null)
    }

    actual fun retrieve(key: String): String? {
        val query = dictOf(
            kSecClass to kSecClassGenericPassword,
            kSecAttrAccount to key,
            kSecReturnData to kCFBooleanTrue,
            kSecMatchLimit to kSecMatchLimitOne
        )

        memScoped {
            val dataRef = alloc<ObjCObjectVar<NSData?>>()
            val status = SecItemCopyMatching(query, dataRef.ptr.reinterpret())

            if (status == errSecSuccess) {
                dataRef.value?.let { data ->
                    return data.toByteArray().decodeToString()
                }
            }
        }

        return null
    }

    actual fun remove(key: String) {
        val query = dictOf(
            kSecClass to kSecClassGenericPassword,
            kSecAttrAccount to key
        )

        SecItemDelete(query)
    }
}
