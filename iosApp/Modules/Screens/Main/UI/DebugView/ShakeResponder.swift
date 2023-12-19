import SwiftUI
import UIKit

struct ShakeResponder: ViewModifier {
	let handler: () -> ()

	func body(content: Content) -> some View {
		content
			.onReceive(NotificationCenter.default.publisher(for: .deviceDidShakeNotification)) { _ in
				handler()
			}
	}
}

extension View {
	func onShake(handler: @escaping () -> ()) -> some View {
		modifier(ShakeResponder(handler: handler))
	}
}

extension UIWindow {
	open override func motionEnded(_ motion: UIEvent.EventSubtype, with event: UIEvent?) {
		if motion == .motionShake {
			NotificationCenter.default.post(name: .deviceDidShakeNotification, object: self)
		}
	}
}

// This extension to Notification.Name makes it easier to listen for the custom notification.
extension Notification.Name {
	static let deviceDidShakeNotification = Notification.Name("deviceDidShakeNotification")
}
