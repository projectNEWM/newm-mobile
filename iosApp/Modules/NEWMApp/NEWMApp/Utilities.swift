import SwiftUI

class ModuleClass {}

extension Image {
	init(_ tab: NEWMAppViewModel.Tab) {
		switch tab {
		case .home: self = Image("Home Icon", bundle: Bundle(for: ModuleClass.self))
		case .stars: self = Image("Stars Icon", bundle: Bundle(for: ModuleClass.self))
		case .tribe: self = Image("Community Icon", bundle: Bundle(for: ModuleClass.self))
		case .wallet: self = Image("Wallet Icon", bundle: Bundle(for: ModuleClass.self))
		case .more: self = Image(systemName: "ellipsis")
		}
	}
}

extension Image {
	init(_ tab: NEWMAppViewModel.MoreTab) {
		switch tab {
		case .playlists: self = Image(systemName: "Wallet Icon")
		case .artists: self = Image(systemName: "Wallet Icon")
		case .profile: self = Image(systemName: "Wallet Icon")
		case .search: self = Image(systemName: "Wallet Icon")
		case .genres: self = Image(systemName: "Wallet Icon")
		}
	}
}

extension View {
	var erased: AnyView {
		return AnyView(self)
	}
}

//TODO: remove this
extension NSNotification.Name {
	public static let deviceDidShakeNotification = NSNotification.Name("MyDeviceDidShakeNotification")
}

extension UIWindow {
	open override func motionEnded(_ motion: UIEvent.EventSubtype, with event: UIEvent?) {
		super.motionEnded(motion, with: event)
		NotificationCenter.default.post(name: .deviceDidShakeNotification, object: event)
	}
}
