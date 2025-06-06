import Foundation
import ModuleLinker
import Resolver
import shared

public final class ProfileModule: Module {
	public static let shared = ProfileModule()
	
	public func registerAllServices() {
		Resolver.register {
			ChangePasswordUseCaseProvider().get()
		}
		
		Resolver.register {
			HasWalletConnectionsUseCaseProvider().get()
		}
		
		Resolver.register {
			DisconnectWalletUseCaseProvider().get()
		}
		
		Resolver.register {
			ConnectWalletUseCaseProvider().get()
		}
		
		Resolver.register {
			DeleteCurrentUserUseCaseProvider().get()
		}
		
		Resolver.register {
			SyncWalletConnectionsUseCaseProvider().get()
		}
		
		Resolver.register {
			SyncWalletConnectionsUseCaseProvider().get()
		}
		
		Resolver.register {
			GetWalletConnectionsUseCaseProvider().get()
		}
		
		Resolver.register {
			DisconnectWalletUseCaseProvider().get()
		}
	}
}
#if DEBUG
import Mocks
extension ProfileModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
		MocksModule.shared.registerAllMockedServices(mockResolver: mockResolver)
	}
}
#endif
