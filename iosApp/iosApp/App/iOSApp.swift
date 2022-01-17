import SwiftUI

@main
struct iOSApp: App {
	@StateObject var viewModel = iOSAppViewModel()
	
	var body: some Scene {
		WindowGroup {
			MainView()
				.fullScreenCover(isPresented: .constant(viewModel.loggedInUser == nil)) { } content: {
					LoginView()
				}
		}
	}
}
