import SwiftUI
import Resolver
import ModuleLinker
import shared

@main
struct iOSApp: App {
	var mainViewProvider: MainViewProviding
	
	init() {
		KoinDIKt.doInitKoin()
		UINavigationBar.appearance().tintColor = .white
#if DEBUG
		UserDefaults.standard.set(false, forKey: "_UIConstraintBasedLayoutLogUnsatisfiable")
#endif
		mainViewProvider = Resolver.resolve()
	}

	var body: some Scene {
		WindowGroup {
			mainViewProvider.mainView()
		}
	}
}
