import Foundation
import Data
import Models
import ModuleLinker

class MockUserRepo: UserManaging {
	func deleteUser() async throws {
		
	}
	
	func createUser(nickname: String, email: String, password: String, passwordConfirmation: String, verificationCode: String) async throws {
		
	}
	
	func resetPassword(email: String, password: String, confirmPassword: String, authCode: String) async throws {
		
	}
	
	func fetchCurrentUser() async throws {
		currentUser = User(
			id: "1",
			createdAt: "today",
			firstName: "Joe",
			lastName: "Blow",
			nickname: "Joe Blow",
			pictureUrl: URL(string: "https://resizing.flixster.com/xhyRkgdbTuATF4u0C2pFWZQZZtw=/300x300/v2/https://flxt.tmsimg.com/assets/p175884_k_v9_ae.jpg")!,
			bannerUrl: URL(string: "https://www.sonypictures.com/sites/default/files/styles/max_360x390/public/banner-images/2020-04/stepbrothers_banner_2572x1100_v2.png?h=abc6acbe&itok=7m7ZKbdz")!,
			email: "joe@blow.com"
		)
	}
	
	func updateUserInfo(firstName: String?, lastName: String?, currentPassword: String?, newPassword: String?, confirmNewPassword: String?) async throws {
		firstName.flatMap {
			guard let currentUser else { return }
			self.currentUser = User(id: currentUser.id,
									createdAt: currentUser.createdAt,
									firstName: $0,
									lastName: currentUser.lastName,
									nickname: currentUser.nickname,
									pictureUrl: currentUser.pictureUrl,
									bannerUrl: currentUser.bannerUrl,
									email: currentUser.email)
		}
		lastName.flatMap {
			guard let currentUser else { return }
			self.currentUser = User(id: currentUser.id,
									createdAt: currentUser.createdAt,
									firstName: currentUser.firstName,
									lastName: $0,
									nickname: currentUser.nickname,
									pictureUrl: currentUser.pictureUrl,
									bannerUrl: currentUser.bannerUrl,
									email: currentUser.email)
		}
	}
	
	init() {}
	
	var currentUser: User?
}
