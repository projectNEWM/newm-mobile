import Foundation

public struct AuthToken: Codable {
	public let accessToken: String
	public let refreshToken: String
}

public extension AuthToken {
	var accessTokenExpired: Bool {
		expirationDate(for: accessToken) < Date()
	}
	
	var refreshTokenExpired: Bool {
		expirationDate(for: refreshToken) < Date()
	}
	
	private func expirationDate(for jwtToken: String) -> Date {
		let segments = jwtToken.components(separatedBy: ".")
		guard segments.count == 3 else { fatalError("JWT token expected to have 3 segments.") }
		
		let payloadString = segments[1]
		guard let payloadData = base64UrlDecode(payloadString),
			  let payload = try? JSONSerialization.jsonObject(with: payloadData, options: []),
			  let payloadDict = payload as? [String: Any],
			  let exp = payloadDict["exp"] as? TimeInterval else {
			fatalError("Bad JWT token.")
		}
		
		return Date(timeIntervalSince1970: exp)
	}
	
	private func base64UrlDecode(_ value: String) -> Data? {
		var base64 = value
			.replacingOccurrences(of: "-", with: "+")
			.replacingOccurrences(of: "_", with: "/")
		
		while base64.count % 4 != 0 {
			base64.append("=")
		}
		
		return Data(base64Encoded: base64)
	}
	
}
