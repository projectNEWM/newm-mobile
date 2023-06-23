import Foundation
import Models

public class UserAPI: NEWMAPI {
	public enum Error: Swift.Error {
		case twoFAFailed
		case accountExists
		case unprocessableEntity(cause: String)
	}
	
	var url: URL { stagingURLv1.appending(path: "users") }
		
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
	
	public func getCurrentUser() async throws -> User {
		let request = makeRequest(url: url.appending(path: "me"), body: nil, method: .GET)
		return try await sendRequest(request).decode()
	}
	
	public func update(user: UpdatedUser) async throws {
		let dictBody = try DictEncoder.encodeToDictionary(user)
		let request = makeRequest(url: url.appending(path: "me"), body: dictBody, method: .PATCH)
		do {
			try await sendRequest(request)
		} catch let error as APIError {
			switch error {
			case .httpError(let statusCode, _):
				switch statusCode {
				case 403:
					throw Error.twoFAFailed
				case 409:
					throw Error.accountExists
				default:
					throw error
				}
			default:
				throw error
			}
		} catch {
			throw error
		}
	}
}

public struct UpdatedUser: Encodable {
	public enum Error: Swift.Error {
		case missingConfirmPassword
		case missingCurrentPassword
		case missingAuthCode
	}
	
	var firstName: String?
	var lastName: String?
	var nickname: String?
	var pictureUrl: String?
	var bannerUrl: String?
	var websiteUrl: String?
	var twitterUrl: String?
	var instagramUrl: String?
	var location: String?
	var role: String?
	var genre: String?
	var biography: String?
	var companyName: String?
	var companyLogoUrl: String?
	var companyIpRights: Bool?
	var walletAddress: String?
	var email: String?
	var newPassword: String?
	var confirmPassword: String?
	var currentPassword: String?
	var authCode: String?
	
	public init(firstName: String? = nil, lastName: String? = nil, nickname: String? = nil, pictureUrl: String? = nil, bannerUrl: String? = nil, websiteUrl: String? = nil, twitterUrl: String? = nil, instagramUrl: String? = nil, location: String? = nil, role: String? = nil, genre: String? = nil, biography: String? = nil, companyName: String? = nil, companyLogoUrl: String? = nil, companyIpRights: Bool? = nil, walletAddress: String? = nil, email: String? = nil, newPassword: String? = nil, confirmPassword: String? = nil, currentPassword: String? = nil, authCode: String? = nil) throws {
		if newPassword != nil {
			guard confirmPassword != nil else { throw Error.missingConfirmPassword }
			guard currentPassword != nil else { throw Error.missingCurrentPassword }
		}
		
		if email != nil {
			guard authCode != nil else { throw Error.missingAuthCode }
		}
		
		self.firstName = firstName
		self.lastName = lastName
		self.nickname = nickname
		self.pictureUrl = pictureUrl
		self.bannerUrl = bannerUrl
		self.websiteUrl = websiteUrl
		self.twitterUrl = twitterUrl
		self.instagramUrl = instagramUrl
		self.location = location
		self.role = role
		self.genre = genre
		self.biography = biography
		self.companyName = companyName
		self.companyLogoUrl = companyLogoUrl
		self.companyIpRights = companyIpRights
		self.walletAddress = walletAddress
		self.email = email
		self.newPassword = newPassword
		self.confirmPassword = confirmPassword
		self.currentPassword = currentPassword
		self.authCode = authCode
	}
}
