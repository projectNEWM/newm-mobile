import Foundation
import shared
import Resolver

@MainActor
class CreateAccountViewModel: ObservableObject {
	@Published var email: String = ""
	@Published var password: String = ""
	@Published var confirmPassword: String = ""
	
	@Published var error: String?
	@Published var loading: Bool = false
	
	var nextEnabled: Bool {
		password == confirmPassword &&
		LoginFieldValidator().validate(email: email, password: password)
	}
	
	@Injected private var createAccountUseCase: SignupUseCase
	
	@Published var route: CreateAccountRoute?
	
	func next() {
		loading = true
		route = .codeConfirmation
//		Task {
//			do {
//				try await createAccountUseCase.requestEmailConfirmationCode(email: email)
//				loading = false
//				route = .codeConfirmation(.init(password: password, passwordConfirmation: confirmPassword, email: email))
//			} catch {
//				loading = false
//				self.error = error.localizedDescription
//			}
//		}
	}
}
