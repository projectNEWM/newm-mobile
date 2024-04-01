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
	}
#endif
}
