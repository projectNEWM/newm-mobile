import Foundation
import ModuleLinker
import Resolver
import shared
import Mocks

public final class ProfileModule: Module {
	public static let shared = ProfileModule()
	
	public func registerAllServices() {
		Resolver.register {
			ChangePasswordUseCaseProvider().get() as ChangePasswordUseCase
		}
		
		Resolver.register {
			HasWalletConnectionsUseCaseProvider().get() as HasWalletConnectionsUseCase
		}
		
		Resolver.register {
			DisconnectWalletUseCaseProvider().get() as DisconnectWalletUseCase
		}
	}
	
#if DEBUG
	public func registerAllMockedServices(mockResolver: Resolver = Resolver(child: .main)) {	}
#endif
}
