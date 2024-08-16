import Foundation
import shared

public class MockSignupUseCase: SignupUseCase {
	public func registerUser(email: String, password: String, passwordConfirmation: String, verificationCode: String, humanVerificationCode: String) async throws {
		
	}
	
	public init() {
		
	}
	
	public func registerUser(email: String, password: String, passwordConfirmation: String, verificationCode: String, humanVerificationCode: String) async throws {
		
	}

    public func requestEmailConfirmationCode(email: String, humanVerificationCode: String, mustExists: Bool) async throws {
		
	}
}
