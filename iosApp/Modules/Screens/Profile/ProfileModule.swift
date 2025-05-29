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

        Resolver.register {
            ConnectWalletUseCaseProvider().get() as ConnectWalletUseCase
        }

		Resolver.register {
			DeleteCurrentUserUseCaseProvider().get() as DeleteCurrentUserUseCase
		}
	}
	
#if DEBUG
	public func registerAllMockedServices(mockResolver: Resolver) {
        mockResolver.register {
            $0.resolve(ConnectWalletUseCase.self) as! HasWalletConnectionsUseCase
        }.scope(.cached)

		mockResolver.register {
			MockConnectWalletUseCase() as ConnectWalletUseCase
		}.scope(.cached)
		
		mockResolver.register {
			$0.resolve(ConnectWalletUseCase.self) as! DisconnectWalletUseCase
		}.scope(.cached)
	}
#endif
}
