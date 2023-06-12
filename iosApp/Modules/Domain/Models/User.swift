import Foundation

public struct User: Codable {
	public let id: String
	public let createdAt: String
	public let firstName: String?
	public let lastName: String?
	public let nickname: String?
	public let pictureUrl: URL?
	public let bannerUrl: URL?
	public let email: String?
	
	public init(id: String, createdAt: String, firstName: String, lastName: String, nickname: String, pictureUrl: URL, bannerUrl: URL, email: String) {
		self.id = id
		self.createdAt = createdAt
		self.firstName = firstName
		self.lastName = lastName
		self.nickname = nickname
		self.pictureUrl = pictureUrl
		self.bannerUrl = bannerUrl
		self.email = email
	}

}
