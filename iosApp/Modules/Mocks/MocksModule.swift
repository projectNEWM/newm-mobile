import Foundation
import ModuleLinker
import Resolver
import shared

final public class MocksModule: Module {
	public static var shared = MocksModule()
	
	public func registerAllServices() {
		//Intentionally empty.
	}
#if DEBUG
	public func registerAllMockedServices(mockResolver: Resolver = .mock) {
		mockResolver.register {
			MockUserDetailsUseCase() as UserDetailsUseCase
		}
		
		mockResolver.register {
			MockConnectWalletUseCase() as ConnectWalletUseCase
		}
		
		mockResolver.register {
			MockChangePasswordUseCase() as ChangePasswordUseCase
		}
		
		mockResolver.register {
			MockErrorLogger() as ErrorReporting
		}
	}
#endif
}
