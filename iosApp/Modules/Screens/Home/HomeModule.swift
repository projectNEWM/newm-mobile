import Foundation
import Resolver
import ModuleLinker
import SwiftUI

public final class HomeModule: ModuleProtocol {
	public static let shared = HomeModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as HomeViewProviding
		}
		
		Resolver.register {
			HomeViewModel()
		}
	}
}

extension HomeModule: HomeViewProviding {
	public func homeView() -> AnyView {
		HomeView().erased
	}
}

#if DEBUG
extension HomeModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
		mockResolver.register {
			MockHomeViewUIModelProvider() as HomeViewUIModelProvider
		}
		
		mockResolver.register {
			MockHomeViewActionHandlerProvider() as HomeViewActionHandlerProvider
		}
	}
}
#endif
