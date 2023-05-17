import Foundation

public struct AuthToken: Codable {
	public let accessToken: String
	public let refreshToken: String
}
