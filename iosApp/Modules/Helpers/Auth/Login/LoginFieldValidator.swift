import Foundation

public class LoginFieldValidator {
	public init() {}
	
	public func validate(email: String, password: String) -> Bool {
		return isValidEmail(email) && isValidPassword(password)
	}
	
	private func isPasswordValid(password: String) -> Bool {
		return isValidPassword(password)
	}
	
	private func isValidEmail(_ email: String) -> Bool {
		let emailAddressRegex = try? NSRegularExpression(pattern: "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
																	"\\@" +
																	"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
																	"(" +
																	"\\." +
																	"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
																	")+")
		let emailRange = NSRange(location: 0, length: email.utf16.count)
		return emailAddressRegex?.firstMatch(in: email, options: [], range: emailRange) != nil
	}
	
	private func isValidPassword(_ password: String) -> Bool {
		return !password.isEmpty && password.count > 3
	}
}
