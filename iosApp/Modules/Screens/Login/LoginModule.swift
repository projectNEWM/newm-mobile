import Foundation
import ModuleLinker
import Resolver
import SwiftUI
import shared
import Mocks

public final class LoginModule: Module {
	public static var shared = LoginModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as LoginViewProviding
		}
		
		Resolver.register {
			LoginUseCaseProvider().get()
		}
		
		Resolver.register {
			SignupUseCaseProvider().get()
		}
		
		Resolver.register {
			ResetPasswordUseCaseProvider().get() as ResetPasswordUseCase
		}
	}
}

#if DEBUG
extension LoginModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
		mockResolver.register { 
			MockErrorLogger() as ErrorReporting
		}
		
		mockResolver.register {
			MockLoginUseCase() as LoginUseCase
		}
		
		mockResolver.register {
			MockSignupUseCase() as SignupUseCase
		}
	}
}
#endif
