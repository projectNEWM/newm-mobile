import Foundation
import shared
import Utilities

public class MockLoginUseCase: LoginUseCase {
	@UserDefault(defaultValue: false)
	var isLoggedIn
	
	public init() {}
	
	public func logIn(email: String, password: String, humanVerificationCode: String) async throws {
		isLoggedIn = true
	}
		
	public func logInWithApple(idToken: String, humanVerificationCode: String) async throws {
		isLoggedIn = true
	}
		
	public func logInWithFacebook(accessToken: String) async throws {
		isLoggedIn = true
	}
	
	public func logInWithGoogle(idToken: String, humanVerificationCode: String) async throws {
		isLoggedIn = true
	}
	
	public func logInWithLinkedIn(accessToken: String) async throws {
		isLoggedIn = true
	}
	
	public func logout() throws {
		isLoggedIn = false
	}
}
