import Foundation

public class UserAPI: NEWMAPI {
	var url: URL { stagingURLv1.appending(path: "users") }
	
	override public init() {}
	
	public func delete() async throws {
		let request = makeRequest(url: url.appending(path: "me"), body: nil, method: .DELETE)
		try await sendRequest(request)
	}
	
	public func create(nickname: String, email: String, password: String, passwordConfirmation: String, verificationCode: String) async throws {
		let request = makeRequest(url: url, body: [
			"nickname": nickname,
			"email": email,
			"newPassword": password,
			"confirmPassword": passwordConfirmation,
			"authCode": verificationCode
		], method: .POST)
		try await sendRequest(request)
	}
	
	public func resetPassword(email: String, password: String, confirmPassword: String, authCode: String) async throws {
		let request = makeRequest(url: url.appending(path: "password"), body: [
			"email": email,
			"newPassword": password,
			"confirmPassword": password,
			"authCode": authCode
		], method: .PUT)
		try await sendRequest(request)
	}
}
