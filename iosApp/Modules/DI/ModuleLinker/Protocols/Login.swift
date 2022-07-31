import Foundation
import SwiftUI
import Combine

public protocol LoginViewProviding {
	func loginView() -> AnyView
}

public protocol LoggedInUserUseCaseProtocol {
	var loggedInUser: AnyPublisher<String?, Never> { get }
}

public protocol LogInUseCaseProtocol {
	func logIn(email: String, password: String) async throws
}

public protocol LogOutUseCaseProtocol {
	func logOut() async throws
}
