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
	public func registerAllMockedServices(mockResolver: Resolver) {
        mockResolver.register {
            MockForceAppUpdateUseCase() as ForceAppUpdateUseCase
        }.scope(.cached)

		mockResolver.register {
			MockUserDetailsUseCase() as UserDetailsUseCase
		}.scope(.cached)
        
        mockResolver.register {
            MockLoginUseCase() as LoginUseCase
        }.scope(.cached)

        mockResolver.register {
            MockSignupUseCase() as SignupUseCase
        }.scope(.cached)

        mockResolver.register {
            $0.resolve(LoginUseCase.self) as! UserSessionUseCase
        }.scope(.cached)
		
		mockResolver.register {
			MockConnectWalletUseCase() as ConnectWalletUseCase
		}.scope(.cached)
		
		mockResolver.register {
			MockChangePasswordUseCase() as ChangePasswordUseCase
		}.scope(.cached)
		
		mockResolver.register {
			MockErrorLogger() as ErrorReporting
		}.scope(.cached)
		
		mockResolver.register {
			MockWalletNFTTracksUseCase() as WalletNFTTracksUseCase
		}.scope(.cached)
		
		mockResolver.register {
            $0.resolve(ConnectWalletUseCase.self) as! HasWalletConnectionsUseCase
        }.scope(.cached)
		
		mockResolver.register {
			MockSyncWalletConnectionsUseCase() as SyncWalletConnectionsUseCase
		}.scope(.cached)

		mockResolver.register {
			MockGetWalletConnectionsUseCase() as GetWalletConnectionsUseCase
		}.scope(.cached)

		mockResolver.register {
			$0.resolve(ConnectWalletUseCase.self) as! DisconnectWalletUseCase
		}.scope(.cached)
	}
#endif
}
