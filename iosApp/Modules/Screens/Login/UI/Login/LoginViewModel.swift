import Foundation
import Combine
import shared
import ModuleLinker
import Resolver
import SharedUI
import shared

@MainActor
class LoginViewModel: ObservableObject {
	@Published var email: String = ""
	@Published var password: String = ""
	@Published var isLoading: Bool = false

	var fieldsAreValid: Bool {
		loginFieldValidator.isEmailValid(email: email) && loginFieldValidator.isPasswordValid(password: password)
	}

	@Injected private var loginFieldValidator: LoginFieldValidator
	@Injected private var logInUseCase: LoginUseCase

	func logIn() {
		Task {
			do {
				isLoading = true
				try await logInUseCase.logIn(email: email, password: password)
				isLoading = false
			} catch {
				print(error)
				isLoading = false
			}
		}
	}

	func forgotPasswordTapped() {
		//TODO:
	}
}
