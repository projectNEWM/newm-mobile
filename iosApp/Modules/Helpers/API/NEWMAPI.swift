import Foundation

enum HTTPMethod: String {
	case DELETE
	case POST
	case PUT
	case GET
}

public enum APIError: Error {
	case invalidResponse
	case httpError(Int)
	case invalidData
}

public enum LoginMethod {
	case facebook(accessToken: String)
	case google(accessToken: String)
	case apple(accessToken: String)
	case email(email: String, password: String)
	
	var path: String? {
		switch self {
		case .facebook: return "facebook"
		case .apple: return "apple"
		case .google: return "google"
		case .email: return nil
		}
	}
}

public class NEWMAPI {
	let tokenManager = AuthTokenManager.shared
	var stagingURLv1: URL { URL(string: "https://garage.newm.io/v1")! }

	@Published public var userIsLoggedIn: Bool = false

	public init() {
		tokenManager.$authToken.map { $0 != nil }.assign(to: &$userIsLoggedIn)
	}
		
	@discardableResult
	func sendRequest(_ request: URLRequest) async throws -> Data {
		let (data, response) = try await URLSession.shared.data(for: request)
		
		print(response)
		print(String(data: data, encoding: .utf8))
		
		guard let httpResponse = response as? HTTPURLResponse else {
			throw APIError.invalidResponse
		}
		
		guard (200...299).contains(httpResponse.statusCode) else {
			throw APIError.httpError(httpResponse.statusCode)
		}
		
		return data
	}
	
	func makeRequest(url: URL, body: [String:String]?, method: HTTPMethod) -> URLRequest {
		var request = URLRequest(url: url)
		request.httpMethod = method.rawValue
		request.setValue("application/json", forHTTPHeaderField: "Content-Type")
		request.setValue("application/json", forHTTPHeaderField: "Accept")
		tokenManager.authToken.flatMap { request.setValue("Bearer \($0.accessToken)", forHTTPHeaderField: "Authorization") }
		request.httpBody = body.flatMap { try! JSONSerialization.data(withJSONObject: $0) }
		
		print(request)
		print(request.httpMethod)
		request.httpBody.flatMap { print(String(data: $0, encoding: .utf8)) }
		print(request.allHTTPHeaderFields)

		return request
	}
}

public extension Data {
	func decode<T: Decodable>() throws -> T {
		guard let object = try? JSONDecoder().decode(T.self, from: self) else {
			throw APIError.invalidData
		}
		return object
	}
}