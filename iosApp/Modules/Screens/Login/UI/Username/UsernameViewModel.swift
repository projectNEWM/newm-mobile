import Foundation

class UsernameViewModel: ObservableObject {
	@Published var username: String = ""
	@Published var route: UsernameRoute?
	@Published var usernamesTaken: Bool = false

	func isValid() -> Bool {
		return username.count > 0
	}

	func nextTapped() {
		route = .done(username: username)
	}
}
