import Foundation
import Resolver
import ModuleLinker
import SwiftUI

public final class MarketplaceModule: Module {
	public static let shared = MarketplaceModule()
	
	public func registerAllServices() {
		Resolver.register {
			do {
				try GetGenresUseCaseFactory().getGenresUseCase()
			} catch {
				print(error)
				fatalError(error.localizedDescription)
			}
		}
	}
}

#if DEBUG
extension MarketplaceModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
		Resolver.register {
			self as MarketplaceViewProviding
		}
	}
}
#endif
