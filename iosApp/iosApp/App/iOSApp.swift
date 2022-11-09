import SwiftUI
import Resolver
import ModuleLinker

@main
struct iOSApp: App {
	var mainViewProvider: MainViewProviding
	
	init() {
		UINavigationBar.appearance().tintColor = .white
#if DEBUG
		UserDefaults.standard.set(false, forKey: "_UIConstraintBasedLayoutLogUnsatisfiable")
#endif
		mainViewProvider = Resolver.resolve()
	}
	
	var body: some Scene {
		WindowGroup {
			mainViewProvider.mainView()
				.preferredColorScheme(.dark)
		}
	}
}
