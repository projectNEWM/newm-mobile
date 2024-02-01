import Foundation
import shared

public class MockSignupUseCase: SignupUseCase {
	public init() {
		
	}
	
	public func registerUser(nickname: String, email: String, password: String, passwordConfirmation: String, verificationCode: String) async throws {
		
	}

	public func requestEmailConfirmationCode(email: String) async throws {
		
	}
}
