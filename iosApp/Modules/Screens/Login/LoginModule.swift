import Foundation
import ModuleLinker
import Resolver
import SwiftUI
import shared

public final class LoginModule: Module {
	public static var shared = LoginModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as LoginViewProviding
		}
		
		Resolver.register {
			LoginUseCaseProvider().get() as LoginUseCase
		}
		
		Resolver.register {
			SignupUseCaseProvider().get() as SignupUseCase
		}
	}
}

//#if DEBUG
extension LoginModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
	}
}
//#endif
