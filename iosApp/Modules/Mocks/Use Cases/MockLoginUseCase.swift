import Foundation
import shared

public class MockLoginUseCase: LoginUseCase {
	public init() {}
	
	public func logIn(email: String, password: String) async throws {
		
	}
		
	public func logInWithApple(idToken: String) async throws {
		
	}
		
	public func logInWithFacebook(accessToken: String) async throws {
		
	}
	
	public func logInWithGoogle(idToken: String) async throws {
		
	}
	
	public func logInWithLinkedIn(accessToken: String) async throws {
		
	}
	
	public func logout() {
		
	}
}
