import Foundation
import Combine
import shared

class LoginViewModel: ObservableObject {
	@Localizable var title = "ENTER_NEWMIVERSE"
	@Localizable var emailPlaceholder = "YOUR_EMAIL"
	@Localizable var passwordPlaceholder = "PASSWORD"
	@Localizable var forgotPassword = "FORGOT_PASSWORD"
	@Localizable var enterNewm = "ENTER_NEWM"
	@Localizable var createAccount = "CREATE_FREE_ACCOUNT"
	
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
