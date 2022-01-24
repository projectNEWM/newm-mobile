import SwiftUI

@main
struct iOSApp: App {
	@StateObject var viewModel = iOSAppViewModel()
	
	init() {
		UINavigationBar.appearance().tintColor = .white
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
