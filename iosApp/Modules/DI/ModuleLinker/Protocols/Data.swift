import Foundation
import Models

public protocol UserManaging: ObservableObject {
	var currentUser: User? { get }

	func deleteUser() async throws
	func createUser(nickname: String, email: String, password: String, passwordConfirmation: String, verificationCode: String) async throws
	func resetPassword(email: String, password: String, confirmPassword: String, authCode: String) async throws
	func fetchCurrentUser() async throws
	func updateUserInfo(firstName: String?, lastName: String?, currentPassword: String?, newPassword: String?, confirmNewPassword: String?) async throws
}

public enum UserRepoError: LocalizedError {
	case accountExists
	case twoFAFailed
	case dataUnavailable
	case displayError(String)
}
