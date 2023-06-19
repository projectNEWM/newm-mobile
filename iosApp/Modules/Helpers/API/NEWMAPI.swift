import Foundation

public class NEWMAPI {
	private let tokenManager: AuthTokenManager
	private let dataFetcher: DataFetching
	var stagingURLv1: URL { URL(string: "https://garage.newm.io/v1")! }

	@Published public var userIsLoggedIn: Bool = false
	
	convenience
	public init() {
		self.init(tokenManager: AuthTokenManager.shared, dataFetcher: URLSession.shared)
	}

	init(tokenManager: AuthTokenManager, dataFetcher: DataFetching) {
		self.tokenManager = tokenManager
		self.dataFetcher = dataFetcher
		tokenManager.$authToken.map { $0 != nil }.assign(to: &$userIsLoggedIn)
	}
	
	func setToken(_ token: AuthToken?) {
		tokenManager.authToken = token
	}
	
	@discardableResult
	func sendRequest(_ request: URLRequest) async throws -> Data {
		try await updateTokens()
		var request = request
		tokenManager.authToken.flatMap { request.setValue("Bearer \($0.accessToken)", forHTTPHeaderField: "Authorization") }
		return try await _sendRequest(request)
	}
	
	@discardableResult
	private func _sendRequest(_ request: URLRequest) async throws -> Data {
		request.prettyPrint()
		
		let (data, response) = try await dataFetcher.data(for: request)
		
		print("response: \(response.url)")
		print(String(data: data, encoding: .utf8))
		
		guard let httpResponse = response as? HTTPURLResponse else {
			throw APIError.invalidResponse
		}
		
		print(httpResponse.statusCode)
				
		guard (200...299).contains(httpResponse.statusCode) else {
			let cause = try? JSONDecoder().decode(ErrorResponse.self, from: data).cause
			throw APIError.httpError(statusCode: httpResponse.statusCode, cause: cause)
		}
		
		return data
	}
	
	func makeRequest(url: URL, body: [String:String]?, method: HTTPMethod) -> URLRequest {
		var request = URLRequest(url: url)
		request.httpMethod = method.rawValue
		request.setValue("application/json", forHTTPHeaderField: "Content-Type")
		request.setValue("application/json", forHTTPHeaderField: "Accept")
		request.httpBody = body.flatMap { try! JSONSerialization.data(withJSONObject: $0) }
		
		return request
	}
	
	func updateTokens() async throws {
		if let authToken = tokenManager.authToken {
			switch (authToken.accessTokenExpired, authToken.refreshTokenExpired) {
			case (true, false):
				try await refreshTokens()
			case (true, true):
				tokenManager.authToken = nil
			case (false, _):
				break
			}
		}
	}

	private func refreshTokens() async throws {
		var request = URLRequest(url: stagingURLv1.appending(path: "auth").appending(path: "refresh"))
		tokenManager.authToken.flatMap { request.setValue("Bearer \($0.refreshToken)", forHTTPHeaderField: "Authorization") }
		tokenManager.authToken = try await _sendRequest(request).decode()
	}
}

public extension Data {
	func decode<T: Decodable>() throws -> T {
		try JSONDecoder().decode(T.self, from: self)
	}
}

extension URLRequest {
	func prettyPrint() {
		print("request: \(self)")
		print(self.httpMethod)
		self.httpBody.flatMap { print(String(data: $0, encoding: .utf8)) }
		print(self.allHTTPHeaderFields)
	}
}
