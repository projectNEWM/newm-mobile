import Foundation
import Resolver
import GoogleSignIn
//import FacebookLogin
import AuthenticationServices
import ModuleLinker
import shared

@MainActor
class LandingViewModel: ObservableObject {
	@Published var navPath = [LandingRoute]()
	
	@Published var errors = ErrorSet()
	@Published var isLoading: Bool = false
	
	@Published var confirmationCode = ""
	@Published var email: String = ""
	@Published var password: String = ""
	@Published var confirmPassword: String = ""
	@Published var nickname: String = ""
			
	@Injected private var logInUseCase: any LoginUseCase
	@Injected private var signUpUseCase: any SignupUseCase
	private let loginFieldValidator = shared.LoginFieldValidator()

	var nicknameIsValid: Bool {
		nickname.count > 0
	}
		
	var createAccountFieldsAreValid: Bool {
		password == confirmPassword &&
		loginFieldValidator.validate(email: email, password: password)
	}
	
	var loginFieldsAreValid: Bool {
		loginFieldValidator.validate(email: email, password: password)
	}
	
	var confirmationCodeIsValid: Bool {
		confirmationCode.count == 6
	}
	
	func goToLogin() {
		navPath.append(.login)
	}
	
	func login() {
		isLoading = true
		Task {
			do {
				try await logInUseCase.logIn(email: email, password: password)
			} catch {
				handleError(error)
			}
			isLoading = false
		}
	}
	
	func forgotPassword() {
		navPath.append(.forgotPassword)
	}
	
	func requestPasswordResetCode() {
		navPath.append(.enterNewPassword)
		Task {
			do {
				try await signUpUseCase.requestEmailConfirmationCode(email: email)
			} catch {
				handleError(error)
			}
		}
	}
	
	func resetPassword() {
		//TODO:
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
				try await signUpUseCase.requestEmailConfirmationCode(email: email)
			} catch {
				handleError(error)
			}
		}
	}
	
	func requestNickname() {
		navPath.append(.nickname)
	}
	
	func registerUser() {
		isLoading = true
		Task {
			do {
				try await signUpUseCase.registerUser(nickname: nickname, email: email, password: password, passwordConfirmation: confirmPassword, verificationCode: confirmationCode)
				navPath.append(.done)
			} catch {
				handleError(error)
			}
			
			isLoading = false
		}
	}
	
	func navigateBackTo(_ navRoute: LandingRoute) {
		navPath = Array(navPath.prefix(through: navPath.firstIndex(of: navRoute)!))
	}
	
	func reset() {
		navPath = []
	}
		
	func handleGoogleSignIn(result: GIDSignInResult?, error: Error?) {
		guard let idToken = result?.user.accessToken.tokenString else {
			//TODO: localize
			handleError("Failed to sign in with Google"); return
		}
		
		guard error == nil else {
			handleError(error!); return
		}
		
		isLoading = true
		Task {
			do {
				try await logInUseCase.logInWithGoogle(idToken: idToken)
			} catch {
				handleError(error)
			}
			isLoading = false
		}
	}
	
	func handleAppleSignIn(result: Result<ASAuthorization, Error>) {
		switch result {
		case .success(let authResults):
			guard
				let identityToken = (authResults.credential as? ASAuthorizationAppleIDCredential)?.identityToken,
				let token = String(data: identityToken, encoding: .utf8)
			else {
				self.errors.append(NEWMError(errorDescription: "Could not sign in with Apple"))
				return
			}
			isLoading = true
			Task {
				do {
					try await logInUseCase.logInWithApple(idToken: token)
				} catch {
					handleError(error)
				}
				isLoading = false
			}
		case .failure(let error):
			handleError(error)
		}
	}
}
