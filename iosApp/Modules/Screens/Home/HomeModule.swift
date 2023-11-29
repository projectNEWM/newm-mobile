import Foundation
import Resolver
import ModuleLinker
import SwiftUI
import shared

public final class HomeModule: Module {
	public static let shared = HomeModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as HomeViewProviding
		}
		
		Resolver.register {
			GetCurrentUserUseCaseProvider().get() as GetCurrentUserUseCase
		}
	}
}

//#if DEBUG
extension HomeModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
		mockResolver.register {
			MockHomeViewUIModelProvider(actionHandler: $0.resolve()) as HomeViewUIModelProviding
		}
	}
}
//#endif
