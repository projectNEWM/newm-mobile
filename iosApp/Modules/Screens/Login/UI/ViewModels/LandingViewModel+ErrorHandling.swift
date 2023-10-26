import Foundation
import shared

extension LandingViewModel {
	func handleError(_ error: Error) {
		if let error = error.kmmException {
			handleKotlinError(error)
		} else {
			self.error = error.localizedDescription
		}
	}
	
	private func handleKotlinError(_ kmmException: KMMException) {
		switch kmmException {
		case let kmmException as RegisterException:
			handleRegisterException(kmmException)
		case let kmmException as LoginException:
			handleLoginException(kmmException)
		default:
			break
		}
		self.error = kmmException.message
	}
	
	private func handleRegisterException(_ exception: RegisterException) {
		switch exception {
		case is RegisterException.UserAlreadyExists:
			email = ""
			navigateBackTo(.createAccount)
		case is RegisterException.TwoFactorAuthenticationFailed:
			confirmationCode = ""
			navigateBackTo(.codeConfirmation)
		default:
			break
		}
	}
	
	private func handleLoginException(_ exception: LoginException) {
		switch exception {
		case is LoginException.UserNotFound:
			email = ""
			password = ""
		default:
			break
		}
	}
}

extension String: Error {
	var localizedDescription: String {
		self
	}
}
