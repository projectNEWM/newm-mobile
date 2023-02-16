import Foundation

class LandingViewModel: ObservableObject {
	@Published var route: LandingRoute?
	
	func login() {
		route = .login
	}
	
	func createAccount() {
		route = .createAccount
	}
}
