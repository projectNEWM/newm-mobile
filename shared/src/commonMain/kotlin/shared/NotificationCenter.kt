package shared

expect fun postNotification(name: String)

object Notification {
    const val loginStateChanged = "login state changed"
}