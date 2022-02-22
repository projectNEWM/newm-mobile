import Foundation
import SwiftUI
import Combine
import shared

class LoginViewModel: ObservableObject {
	let title = NSLocalizedString("ENTER_NEWMIVERSE", comment: "")
	let emailPlaceholder = NSLocalizedString("YOUR_EMAIL", comment: "")
	let passwordPlaceholder = NSLocalizedString("PASSWORD", comment: "")
	let forgotPassword = NSLocalizedString("FORGOT_PASSWORD", comment: "")
	let enterNewm = NSLocalizedString("ENTER_NEWM", comment: "")
	let createAccount = NSLocalizedString("CREATE_FREE_ACCOUNT", comment: "")
	
	@Published var email: String = ""
	@Published var password: String = ""
	
	var fieldsAreValid: Bool {
		LoginFieldValidator.companion.validate(email: email, password: password)
	}
		
	@ObservedObject var logInUseCase = LoggedInUserUseCase.shared
	
	func enterNewmTapped() {
		logInUseCase.logIn()
	}
	
	func createAccountTapped() {
		
	}
	
	func forgotPasswordTapped() {
		
	}
}
