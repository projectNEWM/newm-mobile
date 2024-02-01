import Foundation

public extension NotificationCenter {
	func publisher(for name: String) -> Publisher {
		publisher(for: Notification.Name(name), object: nil)
	}
}
