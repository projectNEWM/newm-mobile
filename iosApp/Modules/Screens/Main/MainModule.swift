import Foundation
import Resolver
import SwiftUI
import ModuleLinker
import shared

public final class MainModule: Module {
	public static let shared = MainModule()

	public func registerAllServices() {
		Resolver.register {
			UserSessionUseCaseProvider().get() as UserSessionUseCase
		}

		// Public
		Resolver.register {
			self as MainViewProviding
		}
	}
}

extension MainModule: MainViewProviding {
	public func mainView() -> AnyView {
		MainView().erased
	}
}

#if DEBUG
extension MainModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
		mockResolver.register {
			MockWalletNFTTracksUseCase() as WalletNFTTracksUseCase
		}
	}
}
#endif
