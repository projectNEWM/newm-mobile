import Foundation
import API

public enum UserManagerError: Error {
	case accountExists
	case twoFAFailed
}

public class UserManager {
	private let api = UserAPI()
	private let loginManager = LoginManager()
	
	public init() {}
	
	public func deleteUser() async throws {
		try await api.delete()
		loginManager.logOut()
	}
	
	public func createUser(nickname: String, email: String, password: String, passwordConfirmation: String, verificationCode: String) async throws {
		do {
			try await api.create(nickname: nickname, email: email, password: password, passwordConfirmation: passwordConfirmation, verificationCode: verificationCode)
		} catch let error as APIError {
			switch error {
			case .httpError(let statusCode):
				switch statusCode {
				case 403:
					throw UserManagerError.twoFAFailed
				case 409:
					throw UserManagerError.accountExists
				default:
					throw error
				}
			default: throw error
			}
		}
	}
}
