import Foundation
import ModuleLinker

struct LoginFieldValidator {
	private let passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$"
	private let emailRegex = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
	
	func validate(email: String, password: String, confirmPassword: String? = nil) throws {
		try validateEmail(email)
		try validatePassword(password)
		if let confirmPassword {
			try validatePasswordsMatch(password, confirmPassword)
		}
	}
	
	private func validatePassword(_ password: String) throws {
		guard password.range(of: passwordRegex, options: .regularExpression) != nil else {
			throw LoginValidationError.invalidPassword
		}
	}
	
	private func validateEmail(_ email: String) throws {
		guard email.range(of: emailRegex, options: .regularExpression) != nil else {
			throw LoginValidationError.invalidEmail
		}
	}
	
	private func validatePasswordsMatch(_ password: String, _ confirmPassword: String) throws {
		guard password == confirmPassword else {
			throw LoginValidationError.passwordsDoNotMatch
		}
	}
}
