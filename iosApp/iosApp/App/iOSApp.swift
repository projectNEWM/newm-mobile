import SwiftUI
import Main

@main
struct iOSApp: App {
	init() {
		UINavigationBar.appearance().tintColor = .white
#if DEBUG
		UserDefaults.standard.set(false, forKey: "_UIConstraintBasedLayoutLogUnsatisfiable")
#endif
	}
	
	var body: some Scene {
		WindowGroup {
			Main()
		}
	}
}
