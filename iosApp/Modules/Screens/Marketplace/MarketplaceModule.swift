import Foundation
import Resolver
import ModuleLinker
import SwiftUI

public final class MarketplaceModule: ModuleProtocol {
	public static let shared = MarketplaceModule()
	
	public func registerAllServices() {
		
	}
}

#if DEBUG
extension MarketplaceModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
		Resolver.register {
			self as MarketplaceViewProviding
		}
		
		Resolver.register {
			MarketplaceViewModel()
		}
	}
}
#endif
