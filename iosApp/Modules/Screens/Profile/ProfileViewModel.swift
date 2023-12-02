import Foundation
import Resolver
import Models
import ModuleLinker
import Combine
import shared

@MainActor
final class ProfileViewModel: ObservableObject {
	@Injected private var getCurrentUser: UserDetailsUseCase
	@Injected private var connectWalletUseCase: ConnectWalletUseCase
	
	@Published var user: User?
	
	var nickName: String { (user?.nickname).emptyIfNil }
	var email: String { (user?.email).emptyIfNil }
	@Published var currentPassword: String = ""
	@Published var newPassword: String = ""
	@Published var confirmPassword: String = ""
	
	@Published var error: String?
	@Published var isLoading: Bool = false
	@Published var isWalletConnected: Bool = false
	
	private var cancels = Set<AnyCancellable>()
	
	var ptrOffset: CGFloat = 0
	
	var showSaveButton: Bool {
//		guard let user = user else { return false }
//		
//		func hasNewPassword() -> Bool {
//			currentPassword.isEmpty == false &&
//			newPassword.isEmpty == false &&
//			confirmPassword.isEmpty == false
//		}
//		
//		func infoFieldsAreEmpty() -> Bool {
//			firstName.isEmpty &&
//			lastName.isEmpty &&
//			currentPassword.isEmpty
//		}
//		
//		func hasNewInfo() -> Bool {
//			firstName != user.firstName ||
//			lastName != user.lastName ||
//			email != user.email
//		}
//		
//		return hasNewPassword() || (infoFieldsAreEmpty() == false && hasNewInfo())
		false
	}
	
	init() {
		isWalletConnected = connectWalletUseCase.isConnected()
		NotificationCenter.default.publisher(for: shared.Notification().walletConnectionStateChanged)
			.sink { [weak self] _ in
				self?.isWalletConnected = self?.connectWalletUseCase.isConnected() == true
			}
			.store(in: &cancels)
		Task {
			isLoading = true
			await loadUser()
			isLoading = false
		}
	}
	
	func loadUser() async {
//		// don't set loading state here, since this might be called from the view's "refreshable"
		do {
			user = try await getCurrentUser.fetchLoggedInUserDetails()
		} catch {
			self.error = error.localizedDescription
		}
	}
	
	func save() {
		Task {
			isLoading = true
//			do {
////				try await userRepo.updateUserInfo(
////					firstName: firstName,
////					lastName: lastName,
////					currentPassword: currentPassword,
////					newPassword: newPassword,
////					confirmNewPassword: confirmPassword
////				)
//			} catch {
//				self.error = error.localizedDescription
//			}
			isLoading = false
		}
	}
	
	func disconnectWallet() {
		connectWalletUseCase.disconnect()
	}
}
