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

public class LoggedInUserUseCase: ObservableObject {
	public static var shared = LoggedInUserUseCase()

	@Published public var loggedInUser: String? = nil

	private init() {}

	public func logIn() {
		loggedInUser = "mulrich@projectnewm.io"
	}

	public func logOut() {
		loggedInUser = nil
	}
}
