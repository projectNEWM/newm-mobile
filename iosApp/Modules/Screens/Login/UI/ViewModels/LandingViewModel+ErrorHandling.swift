import Foundation
import shared
import ModuleLinker
import Resolver
import SharedExtensions
import Utilities

extension LandingViewModel {
	func handleError(_ error: Error) {
		Resolver.resolve(ErrorReporting.self).logError(error)
		if let error = error.kmmException {
			handleKotlinError(error)
		} else {
			self.errors.append(error.newmError)
		}
	}
	
	private func handleKotlinError(_ kmmException: KMMException) {
		switch kmmException {
		case let kmmException as RegisterException:
			handleRegisterException(kmmException)
		default:
			break
		}
		errors.append(kmmException.newmError)
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
}
