import Foundation
import shared

public class MockLoginUseCase: LoginUseCase {
	public init() {}
	
	public func logIn(email: String, password: String, humanVerificationCode: String) async throws {
		
	}
		
	public func logInWithApple(idToken: String, humanVerificationCode: String) async throws {
		
	}
		
	public func logInWithFacebook(accessToken: String) async throws {
		
	}
	
	public func logInWithGoogle(idToken: String, humanVerificationCode: String) async throws {
		
	}
	
	public func logInWithLinkedIn(accessToken: String) async throws {
		
	}
	
	public func logout() {
		
	}
}
