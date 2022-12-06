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

#if DEBUG
extension HomeModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
		mockResolver.register {
			MockHomeViewUIModelProvider(actionHandler: mockResolver.resolve()) as HomeViewUIModelProviding
		}
		
		mockResolver.register {
			MockHomeActionHandler() as HomeViewActionHandling
		}
	}
}
#endif
