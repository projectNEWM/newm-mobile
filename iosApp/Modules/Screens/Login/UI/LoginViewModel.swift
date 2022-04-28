import Foundation
import Combine
import shared
import ModuleLinker

class LoginViewModel: ObservableObject {
	@Localizable(LoginModule.self) var title = .title
	@Localizable(LoginModule.self) var emailPlaceholder = .emailPlaceholder
	@Localizable(LoginModule.self) var passwordPlaceholder = .passwordPlaceholder
	@Localizable(LoginModule.self) var forgotPassword = .forgotPassword
	@Localizable(LoginModule.self) var enterNewm = .enterNewm
	@Localizable(LoginModule.self) var createAccount = .createAccount
	
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
