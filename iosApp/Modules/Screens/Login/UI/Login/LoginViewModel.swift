import Foundation
import Combine
import shared
import ModuleLinker
import Resolver
import SharedUI
import shared

class LoginViewModel: ObservableObject {
	//TODO: move localizable to strings file
	@Localizable(LoginModule.self) var title = .title
	@Localizable(LoginModule.self) var emailPlaceholder = .emailPlaceholder
	@Localizable(LoginModule.self) var passwordPlaceholder = .passwordPlaceholder
	@Localizable(LoginModule.self) var forgotPassword = .forgotPassword
	@Localizable(LoginModule.self) var enterNewm = .enterNewm
	@Localizable(LoginModule.self) var createAccount = .createAccount
	
	@Published var email: String = ""
	@Published var password: String = ""
	@Published var state: ViewState<Void> = .loaded(())
	@Published var route: LoginRoute?
	
	var fieldsAreValid: Bool {
		loginFieldValidator.isEmailValid(email: email) && loginFieldValidator.isPasswordValid(password: password)
	}
	
	@Injected private var loginFieldValidator: LoginFieldValidator
	@Injected private var logInUseCase: LoginUseCase

	func enterNewmTapped() {
		state = .loading
		Task { @MainActor in
			do {
				try await logInUseCase.logIn(email: email, password: password)
//				//TODO: remove this when KMM sets up use case
//				MockLogInLogOutUseCase.shared._loggedInUser.send(email)
				state = .loaded(())
			} catch {
				state = .error(error)
			}
		}
	}
	
	func createAccountTapped() {
		route = .createAccount
	}
	
	func forgotPasswordTapped() {
		//TODO:
	}
}
