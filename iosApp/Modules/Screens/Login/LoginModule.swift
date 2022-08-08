import Foundation
import ModuleLinker
import Resolver
import SwiftUI

public final class LoginModule: ModuleProtocol {
	public static var shared = LoginModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as LoginViewProviding
		}
		
		Resolver.register {
			LoginViewModel()
		}
		
		Resolver.register {
			self as CreateAccountViewProviding
		}
	}
}

#if DEBUG
extension LoginModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
		mockResolver.register {
			MockLogInLogOutUseCase.shared as LoggedInUserUseCaseProtocol
		}
		
		mockResolver.register {
			MockLogInLogOutUseCase.shared as LogInUseCaseProtocol
		}
		
		mockResolver.register {
			MockLogInLogOutUseCase.shared as LogOutUseCaseProtocol
		}
	}
}
#endif
