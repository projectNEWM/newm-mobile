import Foundation
import Resolver
import ModuleLinker
import shared

public final class LibraryModule: Module {
	public static let shared = LibraryModule()
		
	public func registerAllServices() {
		Resolver.register {
			self as LibraryViewProviding
		}
		
		Resolver.register {
			WalletNFTTracksUseCaseProvider().get() as WalletNFTTracksUseCase
		}
		
		Resolver.register {
			ConnectWalletUseCaseProvider().get() as ConnectWalletUseCase
		}
    }
}

#if DEBUG
extension LibraryModule {
    public func registerAllMockedServices(mockResolver: Resolver) {
		mockResolver.register {
			MockWalletNFTTracksUseCase() as WalletNFTTracksUseCase
		}
		
		mockResolver.register {
			MockConnectWalletUseCase() as ConnectWalletUseCase
		}
	}
}
#endif
