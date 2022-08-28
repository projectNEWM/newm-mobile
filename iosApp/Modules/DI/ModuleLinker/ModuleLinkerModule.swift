import Foundation
import Resolver

public final class ModuleLinkerModule: ModuleProtocol {
	public static var shared = ModuleLinkerModule()
	
	public func registerAllServices() {
		
	}
}

#if DEBUG
extension ModuleLinkerModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
		
	}
}
#endif
