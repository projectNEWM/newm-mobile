import Foundation
import shared
import Utilities

public class MockLoginUseCase: LoginUseCase, UserSessionUseCase {
    @UserDefault(defaultValue: true)
    var _isLoggedIn

    public init() {}
    
    public func isLoggedIn() -> Bool {
        _isLoggedIn
    }
    
    public func isLoggedInFlow() -> any Kotlinx_coroutines_coreFlow {
        fatalError()
    }    
	
	public func logIn(email: String, password: String, humanVerificationCode: String) async throws {
        _isLoggedIn = true
	}
		
	public func logInWithApple(idToken: String, humanVerificationCode: String) async throws {
        _isLoggedIn = true
	}
		
	public func logInWithFacebook(accessToken: String) async throws {
        _isLoggedIn = true
	}
	
	public func logInWithGoogle(idToken: String, humanVerificationCode: String) async throws {
        _isLoggedIn = true
	}
	
	public func logInWithLinkedIn(accessToken: String) async throws {
        _isLoggedIn = true
	}
	
	public func logout() throws {
        _isLoggedIn = false
	}
}
