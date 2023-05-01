import Foundation

public class LoginAPI: NEWMAPI {
	private var authAPI: URL { stagingURLv1.appending(path: "auth") }
	private var loginAPI: URL { authAPI.appending(path: "login") }
	
	override public init() {}
	
	public func login(method: LoginMethod) async throws {
		let request = makeLoginPostRequest(loginMethod: method)
		tokenManager.authToken = try await sendRequest(request).decode()
	}
	
	public func logOut() {
		tokenManager.authToken = nil
	}
	
	public func requestEmailVerificationCode(for email: String) async throws {
		let request = makeRequest(url: authAPI.appending(path: "code").appending(queryItems: [URLQueryItem(name: "email", value: email)]), body: nil, method: .GET)
		try await sendRequest(request)
	}
}

extension LoginAPI {
	private func makeLoginPostRequest(loginMethod: LoginMethod) -> URLRequest {
		let requestBody = {
			switch loginMethod {
			case .email(let email, let password):
				return ["email": email, "password": password]
			case .apple(let accessToken):
				return ["accessToken": accessToken]
			case .facebook(let accessToken):
				return ["accessToken": accessToken]
			case .google(let accessToken):
				return ["accessToken": accessToken]
			}
		}()
		
		var url = loginAPI
		loginMethod.path.flatMap { url = url.appending(path: $0) }
		
		return makeRequest(url: url, body: requestBody, method: .POST)
	}
}
