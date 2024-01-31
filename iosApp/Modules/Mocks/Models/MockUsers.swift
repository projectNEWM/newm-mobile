import Foundation
import shared

public extension User {
	static var mocks: [User] {
		UserMocksKt.mockUsers
	}
	
	static var mock: User {
		mocks.first!
	}
	
	static var bannerlessUser: User {
		User(id: "", createdAt: "", oauthType: "", oauthId: "", firstName: "", lastName: "", nickname: "", pictureUrl: "", bannerUrl: nil, websiteUrl: "", twitterUrl: "", instagramUrl: "", location: "", role: "", genre: "", biography: "", walletAddress: "", email: "", companyName: "", companyLogoUrl: "", companyIpRights: "", verificationStatus: "", currentPassword: "", newPassword: "", confirmPassword: "")
	}
}
