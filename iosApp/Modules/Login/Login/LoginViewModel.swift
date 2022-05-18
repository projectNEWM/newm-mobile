import Foundation
import Combine
import shared

class LoginViewModel: ObservableObject {
	@Localizable var title = .title
	@Localizable var emailPlaceholder = .emailPlaceholder
	@Localizable var passwordPlaceholder = .passwordPlaceholder
	@Localizable var forgotPassword = .forgotPassword
	@Localizable var enterNewm = .enterNewm
	@Localizable var createAccount = .createAccount
	
	@Published var email: String = ""
	@Published var password: String = ""
	
	private let loginFieldValidator = LoginFieldValidator()
	
	var fieldsAreValid: Bool {
		loginFieldValidator.validate(email: email, password: password)
	}
		
	let logInUseCase = LoggedInUserUseCase.shared
	
	func enterNewmTapped() {
		logInUseCase.logIn()
	}
	
	func createAccountTapped() {
		
	}
	
	func forgotPasswordTapped() {
		
	}
}

class LoggedInUserUseCase: ObservableObject {
	static var shared = LoggedInUserUseCase()

	@Published var loggedInUser: String? = nil

	private init() {}

	func logIn() {
		loggedInUser = "mulrich@projectnewm.io"
	}

	func logOut() {
		loggedInUser = nil
	}
}
