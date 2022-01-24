import SwiftUI

@main
struct iOSApp: App {
	@StateObject var viewModel = iOSAppViewModel()
	
	init() {
		UINavigationBar.appearance().tintColor = .white
		UINavigationBar.appearance().barTintColor = .white
		UIView.appearance().tintColor = .white
	}
	
	var body: some Scene {
		WindowGroup {
			MainView()
				.fullScreenCover(isPresented: .constant(viewModel.loggedInUser == nil)) { } content: {
					LoginView()
				}
		}
	}
}
