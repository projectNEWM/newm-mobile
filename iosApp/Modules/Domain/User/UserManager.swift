import Foundation
import API
import Models
import ModuleLinker

public enum UserManagerError: Error {
	case accountExists
	case twoFAFailed
	case dataUnavailable
	case displayError(String)
}

public protocol UserManaging: ObservableObject {
	var currentUser: User? { get }
}

public class UserManager: UserManaging, ObservableObject {
	private let api = UserAPI()
	
	@Published public var currentUser: User?
	
	//TODO: make actor
	public static let shared = UserManager()
	
	private init() {}
	
	public func deleteUser() async throws {
		try await execute { try await api.delete() }
		LoginManager.shared.logOut()
	}
	
	public func createUser(nickname: String, email: String, password: String, passwordConfirmation: String, verificationCode: String) async throws {
		try await execute { try await api.create(nickname: nickname, email: email, password: password, passwordConfirmation: passwordConfirmation, verificationCode: verificationCode) }
	}
	
	public func resetPassword(email: String, password: String, confirmPassword: String, authCode: String) async throws {
		try await execute { try await api.resetPassword(email: email, password: password, confirmPassword: confirmPassword, authCode: authCode) }
	}
	
	public func fetchCurrentUser() async throws {
		try await execute { currentUser = try await api.getCurrentUser() }
	}
	
	public func updateUserInfo(firstName: String?, lastName: String?, currentPassword: String?, newPassword: String?, confirmNewPassword: String?) async throws {
		let updatedUser = try UpdatedUser(
			firstName: firstName?.nilIfEmpty,
			lastName: lastName?.nilIfEmpty,
			newPassword: newPassword?.nilIfEmpty,
			confirmPassword: confirmNewPassword?.nilIfEmpty,
			currentPassword: currentPassword?.nilIfEmpty
		)
		try await execute { try await api.update(user: updatedUser) }
		try await fetchCurrentUser()
	}
	
	private func execute(_ block: () async throws -> ()) async throws {
		do {
			try await block()
		} catch let error as UserAPI.Error {
			switch error {
			case .accountExists:
				throw UserManagerError.accountExists
			case .twoFAFailed:
				throw UserManagerError.twoFAFailed
			case .unprocessableEntity(let cause):
				throw UserManagerError.displayError(cause)
			}
		} catch let error as APIError {
			switch error {
			case .invalidResponse:
				throw UserManagerError.dataUnavailable
			case .httpError(_, let cause):
				throw UserManagerError.displayError(cause ?? "An unknown error occurred.")
			}
		}
	}
}
