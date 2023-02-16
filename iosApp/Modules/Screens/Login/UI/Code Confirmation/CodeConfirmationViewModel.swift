import Foundation
import shared
import Resolver

class CodeConfirmationViewModel: ObservableObject {
	@Published var code: String = ""
	@Published var error: String?
	@Published var loading: Bool = false
	
	@Published var route: CodeConfirmationRoute?
	
	@Injected private var signupUserUseCase: SignupUseCase

	func resendCode() {
		
	}
	
	func confirmTapped() {
		loading = true
//		Task { @MainActor in
//			defer {
//				loading = false
//			}
//			do {
//				try await signupUserUseCase.registerUser(email: input.email, password: input.password, passwordConfirmation: input.passwordConfirmation, verificationCode: code)
//			} catch {
//				self.error = error.localizedDescription
//			}
//		}
		route = .username
	}
}
