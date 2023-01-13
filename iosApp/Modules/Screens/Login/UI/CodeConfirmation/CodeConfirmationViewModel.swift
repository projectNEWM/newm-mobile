import Foundation
import shared
import Resolver

class CodeConfirmationViewModel: ObservableObject {
	struct Input {
		let password: String
		let passwordConfirmation: String
		let email: String
	}
	
	@Published var code: String = ""
	@Published var error: String?
	@Published var loading: Bool = false
	
	private let input: Input
	
	@Injected private var signupUserUseCase: SignupUseCase
	
	init(_ input: Input) {
		self.input = input
	}
	
	func confirmTapped() {
		loading = true
		Task { @MainActor in
			defer {
				loading = false
			}
			do {
				try await signupUserUseCase.registerUser(email: input.email, password: input.password, passwordConfirmation: input.passwordConfirmation, verificationCode: code)
			} catch {
				self.error = error.localizedDescription
			}
		}
	}
}
