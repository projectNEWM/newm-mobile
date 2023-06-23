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
	}
}

#if DEBUG
extension LoginModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
//		mockResolver.register {
//			MockLogInLogOutUseCase.shared as LoggedInUserUseCaseProtocol
//		}
		
//		mockResolver.register {
//			MockLogInLogOutUseCase.shared as LoginRepo
//		}
		
//		mockResolver.register {
//			MockLogInLogOutUseCase.shared as LogOutUseCaseProtocol
//		}
	}
}
#endif
