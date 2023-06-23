import Foundation
import Resolver
import ModuleLinker
import SwiftUI

public final class DataModule: ModuleProtocol {
	public static let shared = DataModule()
	
	public func registerAllServices() {
		Resolver.register {
			UserRepo.shared as any UserManaging
		}
	}
}

#if DEBUG
extension DataModule {
	public func registerAllMockedServices(mockResolver: Resolver) {		
	}
}
#endif
