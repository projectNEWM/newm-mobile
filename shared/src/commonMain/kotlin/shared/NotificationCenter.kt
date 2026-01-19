package shared

expect fun postNotification(name: String)

object Notification {
    const val LOGIN_STATE_CHANGED = "login state changed"
    const val WALLET_CONNECTION_STATE_CHANGED = "wallet connection state changed"
}
