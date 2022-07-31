import Foundation

public struct AuthTokens: Codable {
	public let authToken: String
	public let refreshToken: String
	
	public init(authToken: String, refreshToken: String) {
		self.authToken = authToken
		self.refreshToken = refreshToken
	}
}

public struct AuthCredentials: Codable {
	public let authTokens: AuthTokens
	public let user: String
	
	public init( authTokens: AuthTokens, user: String) {
		self.authTokens = authTokens
		self.user = user
	}
}
