import Foundation
import Resolver
import ModuleLinker
import SwiftUI

public final class DomainModule: ModuleProtocol {
	public static let shared = DomainModule()
	
	public func registerAllServices() {
		Resolver.register {
			UserManager.shared as any UserManaging
		}
	}
}

#if DEBUG
extension DomainModule {
	public func registerAllMockedServices(mockResolver: Resolver) {		
	}
}
#endif
