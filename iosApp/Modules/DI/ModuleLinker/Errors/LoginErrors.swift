import Foundation

public enum LoginValidationError: Error {
	case invalidPassword
	case invalidEmail
	case passwordsDoNotMatch
}

extension LoginValidationError: LocalizedError {
	public var errorDescription: String? {
		switch self {
		case .invalidPassword:
			return "Password must contain at least 8 characters, 1 uppercase letter, 1 lowercase letter and 1 number."
		case .invalidEmail:
			return "Invalid email format"
		case .passwordsDoNotMatch:
			return "Passwords do not match"
		}
	}
}
