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
		
	let logInUseCase = LoggedInUserUseCase()
	
	func enterNewmTapped() {
		logInUseCase.logIn()
	}
	
	func createAccountTapped() {
		
	}
	
	func forgotPasswordTapped() {
		
	}
}

import SwiftUI
public class LoggedInUserUseCase: ObservableObject {
	@AppStorage("loggedInUser") var loggedInUser: String?

	init() {}

	public func logIn() {
		loggedInUser = "mulrich@projectnewm.io"
	}

	public func logOut() {
		loggedInUser = nil
	}
}
