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
	}
}

#if DEBUG
extension HomeModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
		mockResolver.register {
			MockHomeViewUIModelProvider(actionHandler: $0.resolve()) as HomeViewUIModelProviding
		}
		
		mockResolver.register {
			MockUserRepo() as any UserManaging
		}
	}
}
#endif
