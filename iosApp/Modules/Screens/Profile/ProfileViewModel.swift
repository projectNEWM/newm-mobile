import Foundation
import Resolver
import ModuleLinker
import Combine
import shared
import AudioPlayer
import SharedExtensions
import Utilities

@MainActor
final class ProfileViewModel: ObservableObject {
	@Injected private var getCurrentUser: UserDetailsUseCase
	@Injected private var connectWalletUseCase: ConnectWalletUseCase
	@Injected private var changePasswordUseCase: ChangePasswordUseCase
	
	@Injected private var logger: ErrorReporting
	
	@Published private var user: User?
	
	var fullName: String { "\((user?.firstName).emptyIfNil) \((user?.lastName).emptyIfNil)" }
	var nickname: String { (user?.nickname?.nilIfEmpty.flatMap { "@\($0)" }).emptyIfNil }
	var email: String { (user?.email).emptyIfNil }
	var bannerURL: URL? { user?.bannerUrl.flatMap(URL.init) }
	var pictureURL: URL? { user?.pictureUrl.flatMap(URL.init) }
	
	@Published var currentPassword: String = ""
	@Published var newPassword: String = ""
	@Published var confirmPassword: String = ""
	@Published private var errors = ErrorSet()
	@Published var showLoadingToast: Bool = true
	@Published var showCompletionToast: Bool = false
	@Published var isWalletConnected: Bool = false
	var errorAlert: String? { errors.currentError?.errorDescription }
	
	private var cancels = Set<AnyCancellable>()
	
	var enableSaveButon: Bool {
		let hasNewPassword =
		currentPassword.isEmpty == false &&
		newPassword.isEmpty == false &&
		confirmPassword.isEmpty == false
		
		let newPasswordsMatch = newPassword == confirmPassword
		
		return hasNewPassword && newPasswordsMatch && showLoadingToast == false && showCompletionToast == false
	}
	
	var showSaveButton: Bool {
		currentPassword.isEmpty == false ||
		newPassword.isEmpty == false ||
		confirmPassword.isEmpty == false
	}
	
	init() {
		isWalletConnected = connectWalletUseCase.isConnected()
		NotificationCenter.default.publisher(for: shared.Notification().walletConnectionStateChanged)
			.sink { [weak self] _ in
				self?.isWalletConnected = self?.connectWalletUseCase.isConnected() == true
			}
			.store(in: &cancels)
		
		Task {
			await loadUser()
			showLoadingToast = false
		}
	}
	
	func loadUser() async {
		// don't set loading state here, since this might be called from the view's "refreshable"
		do {
			user = try await getCurrentUser.fetchLoggedInUserDetails()
		} catch {
			logger.logError(error)
			errors.append(error.newmError)
		}
	}
	
	func save() async {
		defer {
			currentPassword = ""
			newPassword = ""
			confirmPassword = ""
		}
		
		showLoadingToast = true
		do {
			try await changePasswordUseCase.changePassword(
				oldPassword: currentPassword,
				newPassword: newPassword,
				confirmPassword: confirmPassword
			)
			showLoadingToast = false
			showCompletionToast = true
			try? await Task.sleep(for: .seconds(1))
			showCompletionToast = false
		} catch {
			showLoadingToast = false
			logger.logError(error)
			errors.append(error.newmError)
		}
	}
	
	func disconnectWallet() {
		connectWalletUseCase.disconnect()
	}
	
	func alertDismissed() {
		errors.popFirstError()
	}
}
