import SwiftUI
import NEWMApp

@main
struct iOSApp: App {
//	@StateObject var viewModel = iOSAppViewModel()
//
//	init() {
//		UINavigationBar.appearance().tintColor = .white
//#if DEBUG
//		UserDefaults.standard.set(false, forKey: "_UIConstraintBasedLayoutLogUnsatisfiable")
//#endif
//	}
//
	var body: some Scene {
		NEWMApp.iOSApp().body
//		WindowGroup {
//			MainView()
//				.fullScreenCover(isPresented: .constant(viewModel.loggedInUser == nil)) { } content: {
//					LoginView()
//				}
//		}
	}
}
