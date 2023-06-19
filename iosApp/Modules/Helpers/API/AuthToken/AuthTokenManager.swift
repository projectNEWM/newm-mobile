import Foundation
import KeychainSwift

class AuthTokenManager {
	@Published var authToken: AuthToken? {
		didSet {
			if let authToken {
				storeAuthToken(authToken)
			} else {
				deleteAuthToken()
			}
		}
	}
	
	private let accessTokenKey: String
	private let refreshTokenKey: String
	
	static let shared = AuthTokenManager()

	init(accessTokenKey: String = "accessToken", refreshTokenKey: String = "refreshToken") {
		self.accessTokenKey = accessTokenKey
		self.refreshTokenKey = refreshTokenKey
		
		let keychain = KeychainSwift()
		guard let accessToken = keychain.get(accessTokenKey), let refreshToken = keychain.get(refreshTokenKey) else {
			return
		}
		authToken = AuthToken(accessToken: accessToken, refreshToken: refreshToken)
	}
	
	private func storeAuthToken(_ authToken: AuthToken) {
		let keychain = KeychainSwift()
		keychain.set(authToken.accessToken, forKey: accessTokenKey)
		keychain.set(authToken.refreshToken, forKey: refreshTokenKey)
	}
	
	private func deleteAuthToken() {
		let keychain = KeychainSwift()
		keychain.delete(accessTokenKey)
		keychain.delete(refreshTokenKey)
	}
}
