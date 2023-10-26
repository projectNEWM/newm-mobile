package shared

import platform.Foundation.NSNotificationCenter

actual fun postNotification(name: String) {
    NSNotificationCenter.defaultCenter.postNotificationName(name, null)
}
