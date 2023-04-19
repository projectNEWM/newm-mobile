import Foundation
import shared
import Resolver

@MainActor
class LandingViewModel: ObservableObject {
	@Published var navPath = [LandingRoute]()
	
	@Published var error: String?
	@Published var isLoading: Bool = false

	@Published var confirmationCode = ""
	@Published var email: String = ""
	@Published var password: String = ""
	@Published var confirmPassword: String = ""
	@Published var username: String = ""
	
	@Published var wantsToBeShown = true
	
	var usernameIsValid: Bool {
		username.count > 0
	}
	
	@Injected private var createAccountUseCase: SignupUseCase
	@Injected private var loginUseCase: LoginUseCase

	var createAccountFieldsAreValid: Bool {
		password == confirmPassword &&
		LoginFieldValidator().validate(email: email, password: password)
	}
	
	var loginFieldsAreValid: Bool {
		LoginFieldValidator().validate(email: email, password: password)
	}

	func goToLogin() {
		navPath.append(.login)
	}
	
	func login() {
		isLoading = true
		Task {
			do {
				try await loginUseCase.logIn(email: email, password: password)
			} catch {
				self.error = error.localizedDescription
			}
			isLoading = false
		}
	}
	
	func createAccount() {
		navPath.append(.createAccount)
	}
		
	func requestVerificationCode() {
		if !navPath.contains(.codeConfirmation) {
			navPath.append(.codeConfirmation)
		}
		Task {
			do {
				try await createAccountUseCase.requestEmailConfirmationCode(email: email)
			} catch {
				self.error = error.localizedDescription
			}
		}
	}
	
	func registerUser() {
		Task {
			do {
				try await createAccountUseCase.registerUser(nickname: username, email: email, password: password, passwordConfirmation: confirmPassword, verificationCode: confirmationCode)
				navPath.append(.username)
			} catch let error as NSError {
				if let exception = error.kotlinException as? KMMException {
					print("Caught custom exception: \(exception.message ?? "No message available")")
				} else {
					print("Caught general exception: \(error.localizedDescription)")
				}
			}
		}
	}
}
