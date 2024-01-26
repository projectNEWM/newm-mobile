import Foundation
import ModuleLinker
import shared

public class MockChangePasswordUseCase: ChangePasswordUseCase {
	@Published public var changePasswordCalled = false
	public var errorToThrow: Error?
	
	public init(errorToThrow: Error? = nil) {
		self.errorToThrow = errorToThrow
	}
	
	public func changePassword(oldPassword: String, newPassword: String, confirmPassword: String) async throws {
		if let errorToThrow {
			throw errorToThrow
		}
		
		guard oldPassword != newPassword, newPassword == confirmPassword else {
			throw "passwords don't match in \(#function)"
		}
		changePasswordCalled = true
	}
}
