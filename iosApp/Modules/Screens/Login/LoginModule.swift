import Foundation
import ModuleLinker
import Resolver
import SwiftUI
import shared

public final class LoginModule: ModuleProtocol {
	public static var shared = LoginModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as LoginViewProviding
		}
		
		Resolver.register {
			do {
				return try LoginUseCaseFactory().loginUseCase()
			} catch {
				print(error)
				fatalError(error.localizedDescription)
			}
		}
		
		Resolver.register {
			do {
				return try SignupUseCaseFactory().signupUseCase()
			} catch {
				print(error)
				fatalError(error.localizedDescription)
			}
		}
		
		Resolver.register {
			LoginFieldValidator()
		}
	}
}

func buttonText(_ text: String) -> some View {
	Text(verbatim: text)
		.padding()
		.frame(maxWidth: .infinity)
		.bold()
}

#if DEBUG
extension LoginModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
//		mockResolver.register {
//			MockLogInLogOutUseCase.shared as LoggedInUserUseCaseProtocol
//		}
		
//		mockResolver.register {
//			MockLogInLogOutUseCase.shared as LoginUseCase
//		}
		
//		mockResolver.register {
//			MockLogInLogOutUseCase.shared as LogOutUseCaseProtocol
//		}
	}
}
#endif
