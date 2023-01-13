import Foundation
import shared
import Resolver

class CreateAccountViewModel: ObservableObject {
	@Published var email: String = ""
	@Published var password: String = ""
	@Published var confirmPassword: String = ""
	
	@Published var error: String?
	@Published var loading: Bool = false
	
	@Injected private var createAccountUseCase: SignupUseCase
	
	var passwordsMatch: Bool { password == confirmPassword }
	
	@Published var route: CreateAccountRoute?
	
	func confirmTapped() {
		loading = true
		Task {
			do {
				try await createAccountUseCase.requestEmailConfirmationCode(email: email)
				loading = false
				route = .codeConfirmation(.init(password: password, passwordConfirmation: confirmPassword, email: email))
			} catch {
				loading = false
				self.error = error.localizedDescription
			}
		}
	}
}
